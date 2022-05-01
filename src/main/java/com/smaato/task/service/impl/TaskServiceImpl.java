package com.smaato.task.service.impl;

import com.smaato.task.exception.TaskException;
import com.smaato.task.service.TaskService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

  private final Set<Long> requests;

  @Override
  public void persist(Long id) {
      if(requests.add(id))
        throw new TaskException("Invalid request id "+ id);
  }
}
