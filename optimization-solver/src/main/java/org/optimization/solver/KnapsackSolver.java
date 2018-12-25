package org.optimization.solver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnapsackSolver {

  private static final Logger LOG = LoggerFactory.getLogger(KnapsackSolver.class);

  private int capacity;
  private List<Integer> weights;
  private List<Integer> values;
  private long startTime;
  private long completeTime;
  /** @return the capacity */
  public int getCapacity() {
    return capacity;
  }

  /** @return the weights */
  public List<Integer> getWeights() {
    return weights;
  }

  /** @return the values */
  public List<Integer> getValues() {
    return values;
  }

  public long timeElapsed() {
    return completeTime - startTime;
  }

  private static class Wrapper {
    private final double valPerWt;
    private final int wt;
    private final int val;
    private final int index;

    public Wrapper(int wt, int val, int index) {
      this.wt = wt;
      this.val = val;
      this.index = index;
      this.valPerWt = (double) val / wt;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder
          .append("Wrapper [valPerWt=")
          .append(valPerWt)
          .append(", wt=")
          .append(wt)
          .append(", val=")
          .append(val)
          .append(", index=")
          .append(index)
          .append("]");
      return builder.toString();
    }
  }

  private static class WrapperComparator implements Comparator<Wrapper> {
    @Override
    public int compare(Wrapper o1, Wrapper o2) {
      return Double.valueOf(o2.valPerWt).compareTo(Double.valueOf(o1.valPerWt));
    }
  }

  // Returns the maximum value that can be put in a knapsack of capacity W
  public List<Integer> solve() {
    LOG.info(
        "Solving knapsack problem with capacity: {}, wts: {} and values: {}",
        capacity,
        weights,
        values);
    startTime = System.nanoTime();
    Queue<Wrapper> greedyQueue = new PriorityQueue<>(new WrapperComparator());
    for (int i = 0; i < weights.size(); i++) {
      greedyQueue.add(new Wrapper(weights.get(i), values.get(i), i));
    }
    LOG.debug("Intermediate greedy queue: {}", greedyQueue);
    List<Integer> indexes = new ArrayList<>();
    int sum = 0;
    while (capacity > 0) {
      Wrapper w = greedyQueue.poll();
      if (capacity - w.wt < 0) break;
      else if (capacity - w.wt >= 0) {
        capacity = capacity - w.wt;
        indexes.add(w.index);
        sum = sum + w.val;
      }
    }
    completeTime = System.nanoTime();
    LOG.info("knapsack value :{}", sum);
    return indexes;
  }

  public static class Builder {
    private int capacity;
    private List<Integer> weights;
    private List<Integer> values;

    public Builder withCapacity(int capacity) {
      this.capacity = capacity;
      return this;
    }

    public Builder withWeights(List<Integer> weights) {
      this.weights = weights;
      return this;
    }

    public Builder withValues(List<Integer> values) {
      this.values = values;
      return this;
    }

    public KnapsackSolver build() {
      return new KnapsackSolver(this);
    }
  }

  private KnapsackSolver(Builder builder) {
    this.capacity = builder.capacity;
    this.weights = builder.weights;
    this.values = builder.values;
  }
}
