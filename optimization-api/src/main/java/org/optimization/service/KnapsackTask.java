package org.optimization.service;

import org.optimization.service.model.Problem;
import org.optimization.service.model.Solution;
import org.optimization.service.model.Task;

public interface KnapsackTask {

  Task taskStatus(String id);

  Solution showSolution(String id);

  Task createTask(Problem problem);
}
