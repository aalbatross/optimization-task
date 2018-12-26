package org.optimization.solver;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

/** Unit test for simple App. */
public class AppTest {

  private void testKnapsack(
      int W, List<Integer> weights, List<Integer> values, List<Integer> result) {
    KnapsackSolver solver =
        new KnapsackSolver.Builder()
            .withCapacity(W)
            .withWeights(weights)
            .withValues(values)
            .build();
    Assert.assertEquals(solver.solve(), result);
  }

  @Test
  public void testKnapsacks() {
    testKnapsack(60, Arrays.asList(10, 20, 33), Arrays.asList(10, 3, 30), Arrays.asList(0, 2));
    testKnapsack(90, Arrays.asList(30, 40, 50), Arrays.asList(10, 3, 3), Arrays.asList(0, 1));
  }
}
