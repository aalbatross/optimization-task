package org.optimization.service.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.optimization.service.KnapsackTask;
import org.optimization.service.config.Configuration;
import org.optimization.service.model.Problem;
import org.optimization.service.model.Solution;
import org.optimization.service.model.Task;

public class KnapsackTaskClient implements KnapsackTask {

  private final Client client;
  private final WebTarget target;

  public KnapsackTaskClient(Configuration configuration) {
    client = ClientBuilder.newClient();
    target = client.target(configuration.baseURL());
  }

  @Override
  public Task taskStatus(String id) {
    return target.path("/knapsack/tasks/").path(id).request().get(Task.class);
  }

  @Override
  public Solution showSolution(String id) {
    return target.path("/knapsack/solutions/").path(id).request().get(Solution.class);
  }

  @Override
  public Task createTask(Problem problem) {
    return target
        .path("/knapsack/tasks/")
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(problem, MediaType.APPLICATION_JSON))
        .readEntity(Task.class);
  }
}
