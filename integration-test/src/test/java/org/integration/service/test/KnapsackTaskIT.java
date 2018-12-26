package org.integration.service.test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.optimization.service.model.Problem;
import org.optimization.service.model.Solution;
import org.optimization.service.model.Task;
import org.optimization.service.model.Task.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class KnapsackTaskIT {

  private static final String STATUS = "/knapsack/tasks/";
  private static final String CREATE_TASK = "/knapsack/tasks/";
  private static final String SOLUTION = "/knapsack/solutions/";
  private static final Logger LOG = LoggerFactory.getLogger(KnapsackTaskIT.class);

  private static Client unauthorisedClient() {
    return ClientBuilder.newClient();
  }

  private String existingTask;
  private String wrongTask = "zksndjks";

  @Test
  public void testCreateTask() {
    Problem problem =
        new Problem.Builder()
            .capacity(90)
            .weights(Arrays.asList(30, 40, 50))
            .values(Arrays.asList(10, 3, 3))
            .build();
    Task task =
        AdminTaskIT.getTarget(unauthorisedClient())
            .path(CREATE_TASK)
            .request()
            .post(Entity.entity(problem, MediaType.APPLICATION_JSON))
            .readEntity(Task.class);
    LOG.info(" Create task done {}", task);
    this.existingTask = task.getTask();
    Assert.assertTrue(task != null);
    Assert.assertTrue(task.getTask() != null);
    Assert.assertTrue(task.getTimestamps() != null);
    Assert.assertTrue(task.getStatus() != null);
    Assert.assertEquals(task.getStatus(), Status.SUBMITTED);
    Assert.assertTrue(task.getTimestamps().getSubmitted() != null);
  }

  @Test(dependsOnMethods = "testCreateTask")
  public void testStatusCorrect() {
    Task statusTask =
        AdminTaskIT.getTarget(unauthorisedClient())
            .path(STATUS)
            .path(existingTask)
            .request()
            .get(Task.class);
    LOG.info("Status of task {} is {}", existingTask, statusTask);
    Assert.assertTrue(statusTask != null);
    Assert.assertTrue(statusTask.getTask() != null);
    Assert.assertTrue(statusTask.getTimestamps() != null);
    Assert.assertTrue(statusTask.getStatus() != null);
    Assert.assertTrue(statusTask.getTimestamps().getSubmitted() != null);
  }

  @Test(dependsOnMethods = "testCreateTask")
  public void testSolutionCorrect() throws InterruptedException {

    Task statusTask =
        AdminTaskIT.getTarget(unauthorisedClient())
            .path(STATUS)
            .path(existingTask)
            .request()
            .get(Task.class);

    while (statusTask.getStatus() != Status.COMPLETED) {
      TimeUnit.SECONDS.sleep(2);
      statusTask =
          AdminTaskIT.getTarget(unauthorisedClient())
              .path(STATUS)
              .path(existingTask)
              .request()
              .get(Task.class);
      LOG.info("checking status and waiting to get completed {}", statusTask);
    }

    Solution solution =
        AdminTaskIT.getTarget(unauthorisedClient())
            .path(SOLUTION)
            .path(existingTask)
            .request()
            .get(Solution.class);
    LOG.info("Executing => solution {}", solution);
    Assert.assertTrue(solution != null);
    Assert.assertTrue(solution.getTask() != null);
    Assert.assertEquals(solution.getTask(), existingTask);
    Assert.assertTrue(solution.getProblem() != null);
    Assert.assertEquals(solution.getProblem().getCapacity(), Integer.valueOf(90));
    Assert.assertEquals(solution.getProblem().getWeights(), Arrays.asList(30, 40, 50));
    Assert.assertEquals(solution.getProblem().getValues(), Arrays.asList(10, 3, 3));

    Assert.assertTrue(solution.getSolution() != null);
    Assert.assertEquals(solution.getSolution().getItems(), Arrays.asList(0, 1));
    Assert.assertTrue(solution.getSolution().getTime() != null);
  }

  @Test
  public void testStatusWrong() {
    Response solution =
        AdminTaskIT.getTarget(unauthorisedClient()).path(SOLUTION).path(wrongTask).request().get();
    Assert.assertEquals(solution.getStatus(), 404);
  }

  @Test
  public void testSolutionWrong() {
    Response statusTask =
        AdminTaskIT.getTarget(unauthorisedClient()).path(STATUS).path(wrongTask).request().get();
    Assert.assertEquals(statusTask.getStatus(), 404);
  }

  @Test
  public void testNullTask() {
    Response task =
        AdminTaskIT.getTarget(unauthorisedClient())
            .path(CREATE_TASK)
            .request()
            .post(Entity.entity(null, MediaType.APPLICATION_JSON));
    Assert.assertEquals(task.getStatus(), 400);
  }

  @Test
  public void testEmpty() {
    Response task =
        AdminTaskIT.getTarget(unauthorisedClient())
            .path(CREATE_TASK)
            .request()
            .post(Entity.entity("", MediaType.APPLICATION_JSON));
    Assert.assertEquals(task.getStatus(), 400);
  }

  @Test
  public void testWrongProblem() {
    Problem problem =
        new Problem.Builder()
            .capacity(90)
            .weights(Arrays.asList(30, 40, 50, 60))
            .values(Arrays.asList(10, 3, 3))
            .build();
    Response task =
        AdminTaskIT.getTarget(unauthorisedClient())
            .path(CREATE_TASK)
            .request()
            .post(Entity.entity(problem, MediaType.APPLICATION_JSON));
    Assert.assertEquals(task.getStatus(), 400);
  }
}
