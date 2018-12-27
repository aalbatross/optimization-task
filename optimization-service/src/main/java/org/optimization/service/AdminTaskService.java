package org.optimization.service;

import org.optimization.persistence.api.TaskService;
import org.optimization.persistence.model.TaskStatusObject;
import org.optimization.service.exception.TaskNotFoundException;
import org.optimization.service.model.Task;
import org.optimization.service.model.Task.Status;
import org.optimization.service.model.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/** Definition of Admin related task for Knapsack Optimiser Service. */
@RestController
public class AdminTaskService implements AdminTask, ApplicationContextAware {

  private static final Logger LOG = LoggerFactory.getLogger(AdminTaskService.class);
  private ApplicationContext context = null;

  @Autowired private TaskService taskService;

  @RequestMapping("/knapsack/admin/tasks")
  public Tasks showAllTasks() {
    return taskStatus(taskService.statusOfAllTasks(Pageable.unpaged()));
  }

  @RequestMapping("/knapsack/admin/shutdown")
  public void shutdown() {
    LOG.info("Shutdown issued, Service getting down gracefully");
    ((ConfigurableApplicationContext) context).close();
  }

  private Tasks taskStatus(Page<TaskStatusObject> taskStatuses) {
    Tasks tasks = new Tasks();
    taskStatuses.forEach(
        taskStatus -> {
          if (taskStatus.getStatus() != null) {
            Status status = Task.Status.valueOf(taskStatus.getStatus().name());
            if (status == Status.COMPLETED) tasks.getCompleted().add(taskStatus.getTask());
            if (status == Status.SUBMITTED) tasks.getSubmitted().add(taskStatus.getTask());
            if (status == Status.STARTED) tasks.getStarted().add(taskStatus.getTask());
          }
        });
    return tasks;
  }

  @RequestMapping(path = "/knapsack/admin/tasks/{id}", method = RequestMethod.DELETE)
  public String deleteTask(@PathVariable(name = "id") String id) {
    LOG.info("Received request to delete the taskId {}", id);
    if (taskService.existsById(id)) {
      taskService.deleteById(id);
      return String.format("%s task is deleted", id);
    }
    throw new TaskNotFoundException();
  }

  @RequestMapping(path = "/knapsack/admin/tasks/", method = RequestMethod.DELETE)
  public void deleteAllTask() {
    LOG.info("Received request to delete all the tasks completed.");
    taskService.deleteAll();
    LOG.info("All the completed tasks are deleted");
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }
}
