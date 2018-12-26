package org.integration.service.client.test;

import java.util.Properties;

import org.optimization.service.client.AdminTaskClient;
import org.optimization.service.config.Configuration;
import org.optimization.service.model.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Web client test for Admin task. */
public class AdminClientIT {

  private String taskCompleted;

  private AdminTaskClient client = getAuthorisedClient();
  private static final Logger LOG = LoggerFactory.getLogger(AdminClientIT.class);

  private AdminTaskClient getAuthorisedClient() {
    Properties props = new Properties();
    props.setProperty(Configuration.BASE_URL_PATH, "http://localhost:8080/");
    props.setProperty(Configuration.ADMIN_USERNAME, "admin");
    props.setProperty(Configuration.ADMIN_PASSWORD, "secret");
    return new AdminTaskClient(Configuration.create(props));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void getUnAuthorisedClient() {
    Properties props = new Properties();
    props.setProperty(Configuration.BASE_URL_PATH, "http://localhost:8080/");
    new AdminTaskClient(Configuration.create(props));
  }

  @Test
  public void showAllTasks() {
    Tasks tasks = client.showAllTasks();
    LOG.info("Task available at the momment with there status {}", tasks);
    Assert.assertTrue(tasks != null);
    this.taskCompleted = tasks.getCompleted().get(0);
  }

  @Test(dependsOnMethods = "showAllTasks")
  public void deleteTask() {
    String str = client.deleteTask(taskCompleted);
    LOG.info("Deleted the task and message is {}", str);
    Assert.assertTrue(str != null && !str.isEmpty());
  }

  @Test(dependsOnMethods = "deleteTask")
  public void deleteAllTask() {
    client.deleteAllTask();
    Tasks tasks = client.showAllTasks();
    int count =
        tasks.getSubmitted().size() + tasks.getStarted().size() + tasks.getCompleted().size();
    Assert.assertTrue(count == 0);
  }
}
