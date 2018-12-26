package org.integration.service.test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.optimization.service.model.Problem;
import org.optimization.service.model.Solution;
import org.optimization.service.model.Task;
import org.optimization.service.model.Task.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * Normal Workflow to create task -> view status and lastly see the solution.
 *
 */
public class CreateTaskWorkFlow {

  private static final String STATUS = "/knapsack/tasks/";
  private static final String CREATE_TASK = "/knapsack/tasks/";
  private static final String SOLUTION = "/knapsack/solutions/";
  private static final Logger LOG = LoggerFactory.getLogger(KnapsackTaskIT.class);

  private static Client unauthorisedClient() {
    return ClientBuilder.newClient();
  }

  @Test
  public void testCreateTaskFlow() throws InterruptedException {
    Problem problem =
        new Problem.Builder()
            .capacity(90)
            .weights(Arrays.asList(30, 40, 50))
            .values(Arrays.asList(10, 3, 3))
            .build();
    String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();

    Task task =
        AdminTaskIT.getTarget(unauthorisedClient())
            .path(CREATE_TASK)
            .request()
            .post(Entity.entity(problem, MediaType.APPLICATION_JSON))
            .readEntity(Task.class);
    LOG.info("Executing {} => Create task done {}", currentMethodName, task);
    Assert.assertTrue(task != null);
    Assert.assertTrue(task.getTask() != null);
    Assert.assertTrue(task.getTimestamps() != null);
    Assert.assertTrue(task.getStatus() != null);
    Assert.assertEquals(task.getStatus(), Status.SUBMITTED);
    Assert.assertTrue(task.getTimestamps().getSubmitted() != null);

    String taskId = task.getTask();

    Task statusTask =
        AdminTaskIT.getTarget(unauthorisedClient())
            .path(STATUS)
            .path(taskId)
            .request()
            .get(Task.class);
    LOG.info("Executing {} => checking status {}", currentMethodName, statusTask);
    Assert.assertTrue(statusTask != null);
    Assert.assertTrue(statusTask.getTask() != null);
    Assert.assertTrue(statusTask.getTimestamps() != null);
    Assert.assertTrue(statusTask.getStatus() != null);
    Assert.assertTrue(statusTask.getTimestamps().getSubmitted() != null);

    while (statusTask.getStatus() != Status.COMPLETED) {
      TimeUnit.SECONDS.sleep(2);
      statusTask =
          AdminTaskIT.getTarget(unauthorisedClient())
              .path(STATUS)
              .path(taskId)
              .request()
              .get(Task.class);
      LOG.info(
          "Executing {} => checking status and waiting to get completed {}",
          currentMethodName,
          statusTask);
    }

    Solution solution =
        AdminTaskIT.getTarget(unauthorisedClient())
            .path(SOLUTION)
            .path(taskId)
            .request()
            .get(Solution.class);
    LOG.info("Executing {} => solution {}", currentMethodName, solution);
    Assert.assertTrue(solution != null);
    Assert.assertTrue(solution.getTask() != null);
    Assert.assertEquals(solution.getTask(), taskId);
    Assert.assertTrue(solution.getProblem() != null);
    Assert.assertEquals(solution.getProblem().getCapacity(), Integer.valueOf(90));
    Assert.assertEquals(solution.getProblem().getWeights(), Arrays.asList(30, 40, 50));
    Assert.assertEquals(solution.getProblem().getValues(), Arrays.asList(10, 3, 3));

    Assert.assertTrue(solution.getSolution() != null);
    Assert.assertEquals(solution.getSolution().getItems(), Arrays.asList(0, 1));
    Assert.assertTrue(solution.getSolution().getTime() != null);
    
  }
}
