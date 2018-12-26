package org.optimization.service.client;

import java.util.Optional;
import java.util.function.Supplier;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.optimization.service.AdminTask;
import org.optimization.service.config.Configuration;
import org.optimization.service.model.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Webclient for invoking Call to Admin related tasks of KO Service. */
public class AdminTaskClient implements AdminTask {

  private static final Logger LOG = LoggerFactory.getLogger(AdminTaskClient.class);
  private static final Supplier<IllegalArgumentException> exc =
      () -> new IllegalArgumentException("Needs admin username and password to use this client");
  private final Client client;
  private final WebTarget target;

  public AdminTaskClient(Configuration configuration) {
    String adminUser =
        (String)
            Optional.ofNullable(configuration.properties().get(Configuration.ADMIN_USERNAME))
                .orElseThrow(exc);
    String adminPass =
        (String)
            Optional.ofNullable(configuration.properties().get(Configuration.ADMIN_PASSWORD))
                .orElseThrow(exc);
    HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(adminUser, adminPass);
    client = ClientBuilder.newClient().register(feature);
    target = client.target(configuration.baseURL());
  }

  @Override
  public Tasks showAllTasks() {
    return target.path("/knapsack/admin/tasks").request().get(Tasks.class);
  }

  @Override
  public void shutdown() {}

  @Override
  public String deleteTask(String id) {
    return target
        .path("/knapsack/admin/tasks/")
        .path(id)
        .request()
        .delete()
        .readEntity(String.class);
  }

  @Override
  public void deleteAllTask() {
    Response response = target.path("/knapsack/admin/tasks/").request().delete();
    if (response.getStatus() == 200) LOG.info("All tasks deleted.");
    else LOG.error("Cannot delete the tasks {}", response.getStatus());
  }
}
