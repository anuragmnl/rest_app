package com.smaato.task.service.impl;

import static com.smaato.task.constant.TaskConstant.TIME_KEY;

import com.smaato.task.exception.TaskException;
import com.smaato.task.service.TaskService;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

  private final Map<String, Set<Long>> requests;


  @Override
  public void persist(Long id, String time) {
    requests.compute(time, (key, value) -> value == null ? new CopyOnWriteArraySet<>(Collections.singleton(id)) : add(value,id));
  }

  @Override
  public int count(String hourmin) {
    return requests.get(hourmin)==null?0:requests.get(hourmin).size();
  }

  private Set<Long> add(Set<Long> value, Long id) {
   boolean flag =  value.add(id);
   if(!flag)
     throw new TaskException(" Duplicate request id "+ id);

   return value;
  }


  @Scheduled(fixedDelay = 60000)
  void logDetails(){
    String presentTime = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_KEY));
    String oneMinEarlier = LocalTime.now().minusMinutes(1).format(DateTimeFormatter.ofPattern(TIME_KEY));
    log.info("Present time [{}] and one min earlier time [{}]",presentTime,oneMinEarlier);
    if(requests.containsKey(oneMinEarlier)){
      log.info("Will remove [{}] for key [{}] details of entries  [{}] ",requests.get(oneMinEarlier).size(),oneMinEarlier,requests.get(oneMinEarlier));
      requests.remove(oneMinEarlier);
    }
  }
}
