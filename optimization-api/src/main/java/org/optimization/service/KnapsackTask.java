package org.optimization.service;

import org.optimization.service.model.Problem;
import org.optimization.service.model.Solution;
import org.optimization.service.model.Task;
/**
 * 
 * Service declaration for Task related service.
 *
 */
public interface KnapsackTask {

  /**
   * check the status of task with provided id.
   * @param id of the task.
   * @return task object.
   */
  Task taskStatus(String id);
  
  /**
   * Show the solution {@linkplain Solution} of the provided task if the task is completed, else gives a 404 not found http code.
   * @param id of task.
   * @return solution of the taskid.
   */
  Solution showSolution(String id);

  /**
   * Submits a task to the system.
   * @param problem definition of the knapsack problem.
   * @return task object with status and id of the task.
   */
  Task createTask(Problem problem);
}
