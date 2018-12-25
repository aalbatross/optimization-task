package org.optimization.service;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.optimization.persistence.api.TaskService;
import org.optimization.persistence.model.ProblemEntity;
import org.optimization.persistence.model.TaskEntity;
import org.optimization.service.exception.SubmitFailedException;
import org.optimization.service.exception.TaskNotFoundException;
import org.optimization.service.model.Problem;
import org.optimization.service.model.Result;
import org.optimization.service.model.Solution;
import org.optimization.service.model.Task;
import org.optimization.service.model.Task.Timestamps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KnapsackTaskService implements KnapsackTask {

  @Autowired private TaskService taskService;

  @Autowired private KafkaTemplate<String, String> template;

  @RequestMapping("/knapsack/tasks/{id}")
  public Task taskStatus(@PathVariable(name = "id") String taskId) {
    TaskEntity task = taskService.findById(taskId).orElseThrow(TaskNotFoundException::new);
    Timestamps ts =
        new Timestamps(
            task.getTimestamps().getSubmitted(),
            task.getTimestamps().getStarted(),
            task.getTimestamps().getCompleted());
    return new Task.Builder()
        .task(task.getId())
        .status(Task.Status.valueOf(task.getStatus().name()))
        .timestamps(ts)
        .build();
  }

  @RequestMapping("/knapsack/solutions/{id}")
  public Solution showSolution(@PathVariable(name = "id") String id) {
    TaskEntity task = taskService.findById(id).orElseThrow(TaskNotFoundException::new);
    if (task.getSolution() == null) throw new TaskNotFoundException();
    Problem problem =
        new Problem(
            task.getProblem().getCapacity(),
            task.getProblem().getWeights(),
            task.getProblem().getValues());
    Result result = new Result(task.getSolution().getItems(), task.getSolution().getTime());
    return new Solution.Builder().task(task.getId()).problem(problem).solution(result).build();
  }

  @RequestMapping(value = "/knapsack/tasks", method = RequestMethod.POST)
  public Task createTask(@RequestBody Problem problem) {
    ProblemEntity pb =
        new ProblemEntity.Builder()
            .capacity(problem.getCapacity())
            .weights(problem.getWeights())
            .values(problem.getValues())
            .build();
    TaskEntity task = new TaskEntity.Builder().problem(pb).build();
    task = taskService.save(task);

    try {
      template.send("foo", task.getId()).get(2, TimeUnit.SECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new SubmitFailedException(e.getMessage());
    }
    task.setStatus(TaskEntity.Status.SUBMITTED);
    task.setTimestamps(new TaskEntity.Timestamps(new Date(), null, null));
    task = taskService.save(task);
    Timestamps tstamp =
        new Timestamps(
            task.getTimestamps().getSubmitted(),
            task.getTimestamps().getStarted(),
            task.getTimestamps().getCompleted());
    return new Task.Builder()
        .task(task.getId())
        .status(Task.Status.valueOf(task.getStatus().name()))
        .timestamps(tstamp)
        .build();
  }
}
