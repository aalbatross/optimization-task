package org.optimization.service.client;

import org.optimization.service.config.Configuration;
import org.optimization.service.model.Tasks;
import org.testng.annotations.Test;

public class AdminTaskClientTest {

  private AdminTaskClient client =
      new AdminTaskClient(Configuration.withConfigurationFile("application.properties"));

  @Test
  public void testAllTasks() {
    Tasks tasks = client.showAllTasks();
    System.out.println(tasks);
  }
}
