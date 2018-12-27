package org.optimization.service;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.optimization.persistence.api.TaskService;
import org.optimization.persistence.model.ProblemEntity;
import org.optimization.persistence.model.TaskEntity;
import org.optimization.persistence.model.TimestampsAssociation;
import org.optimization.service.exception.InvalidKnapsackProblem;
import org.optimization.service.exception.SubmitFailedException;
import org.optimization.service.exception.TaskNotFoundException;
import org.optimization.service.model.Problem;
import org.optimization.service.model.Result;
import org.optimization.service.model.Solution;
import org.optimization.service.model.Task;
import org.optimization.service.model.Timestamps;
import org.optimization.service.model.validation.ProblemValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/** Definition of Task related service for Knapsack Optimiser Service. */
@RestController
public class KnapsackTaskService implements KnapsackTask {

  private static final Logger LOG = LoggerFactory.getLogger(KnapsackTaskService.class);

  @Value("${topic}")
  private String topic;

  @Autowired private TaskService taskService;

  @Autowired private KafkaTemplate<String, String> template;

  @RequestMapping("/knapsack/tasks/{id}")
  public Task taskStatus(@PathVariable(name = "id") String taskId) {
    TaskEntity task = taskService.findById(taskId).orElseThrow(TaskNotFoundException::new);
    Timestamps ts =
        Optional.ofNullable(task.getTimestamps())
            .map(
                tsa ->
                    new Timestamps.Builder()
                        .submitted(tsa.getSubmitted())
                        .started(tsa.getStarted())
                        .completed(tsa.getCompleted())
                        .build())
            .orElse(new Timestamps.Builder().build());

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

  @Transactional
  @RequestMapping(value = "/knapsack/tasks", method = RequestMethod.POST)
  public Task createTask(@RequestBody Problem problem) {
    ProblemValidator validator = ProblemValidator.create(problem);
    if (!validator.isValid()) {
      LOG.info("Knapsack problem has issues {}, due to {}", problem, validator.issues());
      throw new InvalidKnapsackProblem(validator.issues());
    }

    ProblemEntity pb =
        new ProblemEntity.Builder()
            .capacity(problem.getCapacity())
            .weights(problem.getWeights())
            .values(problem.getValues())
            .build();
    TaskEntity task = new TaskEntity.Builder().problem(pb).build();
    task = taskService.save(task);
    LOG.info("Knapsack problem arrived {}, creating task {}", problem, task.getId());
    try {
      template.send(topic, task.getId()).get(2, TimeUnit.SECONDS);
      LOG.info("taskId {}, send to message bus {}", task.getId(), topic);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new SubmitFailedException(e.getMessage());
    }
    task.setStatus(TaskEntity.Status.SUBMITTED);
    task.setTimestamps(
        new TimestampsAssociation.Builder().submitted(Instant.now().getEpochSecond()).build());
    task = taskService.save(task);
    LOG.info("task {} marked submitted.", task.getId());
    Timestamps ts =
        Optional.ofNullable(task.getTimestamps())
            .map(
                tsa ->
                    new Timestamps.Builder()
                        .submitted(tsa.getSubmitted())
                        .started(tsa.getStarted())
                        .completed(tsa.getCompleted())
                        .build())
            .orElse(new Timestamps.Builder().build());
    return new Task.Builder()
        .task(task.getId())
        .status(Task.Status.valueOf(task.getStatus().name()))
        .timestamps(ts)
        .build();
  }
}
