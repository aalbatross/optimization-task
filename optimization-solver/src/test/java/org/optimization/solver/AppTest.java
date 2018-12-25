package org.optimization.solver;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

/** Unit test for simple App. */
public class AppTest {
  @Test
  public void testKnapsack() {
    int W = 60;
    List<Integer> weights = Arrays.asList(10, 20, 33);
    List<Integer> values = Arrays.asList(10, 3, 30);
    KnapsackSolver solver =
        new KnapsackSolver.Builder()
            .withCapacity(W)
            .withWeights(weights)
            .withValues(values)
            .build();
    Assert.assertEquals(solver.solve(), Arrays.asList(0, 2));
  }

  @Test
  public void testKnapsack2() {
    int W = 90;
    List<Integer> weights = Arrays.asList(30, 40, 50);
    List<Integer> values = Arrays.asList(10, 3, 3);
    KnapsackSolver solver =
        new KnapsackSolver.Builder()
            .withCapacity(W)
            .withWeights(weights)
            .withValues(values)
            .build();
    Assert.assertEquals(solver.solve(), Arrays.asList(0, 1));
  }
}
