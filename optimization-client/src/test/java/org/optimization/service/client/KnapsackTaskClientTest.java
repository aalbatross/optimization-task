package org.optimization.service.client;

import java.util.Arrays;

import org.optimization.service.config.Configuration;
import org.optimization.service.model.Problem;
import org.optimization.service.model.Solution;
import org.optimization.service.model.Task;
import org.testng.annotations.Test;

public class KnapsackTaskClientTest {

  private KnapsackTaskClient client =
      new KnapsackTaskClient(Configuration.withConfigurationFile("application.properties"));

  @Test
  public void testCreateTask() {
    Problem problem = new Problem(60, Arrays.asList(10, 20, 30), Arrays.asList(80, 30, 20));
    Task task = client.createTask(problem);
    System.out.println(task);
  }

  @Test
  public void testTaskStatus() {

    Task task = client.taskStatus("fad52a74-0bee-4660-a81e-2fb2e9bc994a");
    System.out.println(task);
  }

  @Test
  public void testShowSolution() {

    Solution solution = client.showSolution("fad52a74-0bee-4660-a81e-2fb2e9bc994a");
    System.out.println(solution);
  }
}
