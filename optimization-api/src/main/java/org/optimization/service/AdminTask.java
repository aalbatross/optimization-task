package org.optimization.service;

import org.optimization.service.model.Tasks;
/**
 * 
 * Admin related service declaration for KO Service.
 *
 */
public interface AdminTask {

  /**
   * show the status of all the tasks submitted to the system.
   * @return tasks.
   */
  Tasks showAllTasks();
  /**
   * shutting down the service.
   */
  void shutdown();
  /**
   * delete task with provided id.
   * @param id of the task to be deleted.
   * @return message
   */
  String deleteTask(String id);
  
  /**
   * delete all tasks and there metadata.
   */
  void deleteAllTask();
}
