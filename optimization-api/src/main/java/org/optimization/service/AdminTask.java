package org.optimization.service;

import org.optimization.service.model.Tasks;

public interface AdminTask {

  Tasks showAllTasks();

  void shutdown();

  String deleteTask(String id);

  void deleteAllTask();
}
