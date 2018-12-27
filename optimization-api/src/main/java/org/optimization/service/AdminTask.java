package org.optimization.service;

import org.optimization.service.model.Tasks;

/** Admin related service declaration for KO Service. */
public interface AdminTask {

  /**
   * show the status of all the tasks submitted to the system.
   *
   * @return tasks.
   */
  Tasks showAllTasks();

  /** shutting down the service. */
  void shutdown();

  /**
   * delete task with provided id.
   *
   * @param id of the task to be deleted.
   * @return message
   */
  String deleteTask(String id);

  /**
   * delete all tasks metadata, but it does'nt remove it from queue, so after execution task may be
   * shown again, use with caution.
   */
  void deleteAllTask();
}
