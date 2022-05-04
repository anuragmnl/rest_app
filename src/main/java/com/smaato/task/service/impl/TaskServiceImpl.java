package com.smaato.task.service.impl;

import static com.smaato.task.constant.TaskConstant.TIME_KEY;

import com.smaato.task.exception.TaskException;
import com.smaato.task.repository.RedisRepository;
import com.smaato.task.service.KafkaProducerService;
import com.smaato.task.service.TaskService;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

  private final Map<String, Set<Long>> requests;

  @Autowired(required = false)
  private KafkaProducerService<String, Long> kafkaProducerService;

  @Autowired(required = false)
  private RedisRepository redisRepository;

  @Value("${kafka-config.topic-name}")
  private String topicName;


  @Override
  public void persist(Long id, String time) {
    if (null != redisRepository) {
      Set<Long> set = redisRepository.get(time);
      if (null != set) {
        if (set.add(id)) {
          redisRepository.add(time, set);
        } else {
          throw new TaskException(" Duplicate request id " + id);
        }
      } else {
        redisRepository.add(time, new CopyOnWriteArraySet<>(Collections.singleton(id)));
      }
    } else {
      requests.compute(time,
          (key, value) -> value == null ? new CopyOnWriteArraySet<>(Collections.singleton(id))
              : add(value, id));
    }
  }

  @Override
  public int count(String hourmin) {
    return null == redisRepository ? getFromLocalCache(hourmin) : getFromExternalCache(hourmin);
  }

  private int getFromExternalCache(String hourmin) {
    return redisRepository.get(hourmin) == null ? 0 : redisRepository.get(hourmin).size();
  }

  private int getFromLocalCache(String hourmin) {
    return requests.get(hourmin) == null ? 0 : requests.get(hourmin).size();
  }

  private Set<Long> add(Set<Long> value, Long id) {
    boolean flag = value.add(id);
    if (!flag) {
      throw new TaskException(" Duplicate request id " + id);
    }

    return value;
  }


  @Scheduled(fixedDelay = 60000)
  void logDetails() {
    String presentTime = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_KEY));
    String oneMinEarlier = LocalTime.now().minusMinutes(1)
                                   .format(DateTimeFormatter.ofPattern(TIME_KEY));
    log.debug("Present time [{}] and one min earlier time [{}]", presentTime, oneMinEarlier);

    if (redisRepository != null && redisRepository.containsKey(oneMinEarlier)) {
      log.info("Count of requestId's recieved is [{}] in interval time [{}] and [{}] from distributed cache",
          redisRepository.getSize(oneMinEarlier), oneMinEarlier,presentTime);
      pushToKafka(redisRepository.getSize(oneMinEarlier));
      boolean flag = redisRepository.remove(oneMinEarlier);
      log.debug("Key [{}] removed [{}]", oneMinEarlier, flag ? "successfully" : "unsuccessflly");
    } else if (requests.containsKey(oneMinEarlier)) {
      log.info("Count of requestId's recieved is [{}] in interval time [{}] and [{}] from  standalone cache",
          requests.get(oneMinEarlier).size(), oneMinEarlier, presentTime);
      pushToKafka((long) requests.get(oneMinEarlier).size());
      Set<Long> flag = requests.remove(oneMinEarlier);
      log.debug("Key [{}] removed [{}]", oneMinEarlier,
          !flag.isEmpty() ? "successfully" : "unsuccessflly");
    } else {
      log.info("No data present for key [{}]", oneMinEarlier);
    }
  }

  private void pushToKafka(Long count) {
    if (kafkaProducerService != null) {
      kafkaProducerService.send(topicName, "COUNT", count);
    } else {
      log.info("Kafka not available");
    }
  }
}
