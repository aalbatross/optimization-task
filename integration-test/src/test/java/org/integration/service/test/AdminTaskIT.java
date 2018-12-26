package org.integration.service.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.optimization.service.model.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminTaskIT {

  private static final String SHOW_ALL_TASKS = "/knapsack/admin/tasks";
  private static final String DELETE_ALL_TASKS = "/knapsack/admin/tasks/";
  private static final String SHUTDOWN = "/knapsack/admin/shutdown";
  private static final Logger LOG = LoggerFactory.getLogger(AdminTaskIT.class);

  private HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "secret");
  private final Client authorisedClient = ClientBuilder.newClient().register(feature);

  private final Client unauthorisedClient = ClientBuilder.newClient();

  public static WebTarget getTarget(Client client) {
    return client.target("http://localhost:8080/");
  }

  @Test
  public void testShowAllTasks() {
    // security test
    Response response = getTarget(unauthorisedClient).path(SHOW_ALL_TASKS).request().get();
    Assert.assertEquals(response.getStatus(), 401);

    // feature test
    Tasks tasks = getTarget(authorisedClient).path(SHOW_ALL_TASKS).request().get(Tasks.class);
    LOG.info("SHOW ALL TASKS: {}", tasks);
    Assert.assertTrue(tasks != null);
    Assert.assertTrue(tasks.getSubmitted() != null);
    Assert.assertTrue(tasks.getStarted() != null);
    Assert.assertTrue(tasks.getCompleted() != null);
  }

  @Test
  public void testdeleteAllTasks() {
    // security test
    Response response = getTarget(unauthorisedClient).path(DELETE_ALL_TASKS).request().get();
    Assert.assertEquals(response.getStatus(), 401);

    // feature test
    Response response2 = getTarget(authorisedClient).path(DELETE_ALL_TASKS).request().get();
    Assert.assertEquals(response2.getStatus(), 200);
  }

  @Test
  public void testShutdownTasks() {
    // security test
    Response response = getTarget(unauthorisedClient).path(SHUTDOWN).request().get();
    Assert.assertEquals(response.getStatus(), 401);
  }
}
