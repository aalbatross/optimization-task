package org.integration.service.client.test;

import java.util.Arrays;
import java.util.Properties;

import org.optimization.service.client.KnapsackTaskClient;
import org.optimization.service.config.Configuration;
import org.optimization.service.model.Problem;
import org.optimization.service.model.Solution;
import org.optimization.service.model.Task;
import org.optimization.service.model.Task.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

/** KO Web Client test for task creation/ task status view and result view. */
public class KnapsackClientIT {

  private String existingTask;
  private KnapsackTaskClient client = getClient();

  private static KnapsackTaskClient getClient() {
    Properties props = new Properties();
    props.setProperty(Configuration.BASE_URL_PATH, "http://localhost:8080/");
    return new KnapsackTaskClient(Configuration.create(props));
  }

  @Test
  public void taskCreateTest() {
    Problem problem =
        new Problem.Builder()
            .capacity(90)
            .weights(Arrays.asList(30, 40, 50))
            .values(Arrays.asList(10, 3, 3))
            .build();

    Task task = client.createTask(problem);
    existingTask = task.getTask();
    Assert.assertTrue(task != null);
  }

  @Test(dependsOnMethods = "taskCreateTest")
  public void checktaskStatusTest() {
    Task task = client.taskStatus(existingTask);
    Assert.assertTrue(task != null);
  }

  @Test(dependsOnMethods = {"taskCreateTest", "checktaskStatusTest"})
  public void checkSolution() {
    while (client.taskStatus(existingTask).getStatus() != Status.COMPLETED) ;
    Solution task = client.showSolution(existingTask);
    Assert.assertTrue(task != null);
    Assert.assertTrue(task.getSolution() != null);
    Assert.assertTrue(task.getSolution().getItems() != null);
    Assert.assertTrue(task.getSolution().getItems().size() > 0);
    Assert.assertTrue(task.getSolution().getTime() != null);
  }
}
