package org.optimization.service.model;

import java.io.Serializable;
import java.util.List;

/** Data Structure defining Problem. */
public class Problem implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private Integer capacity;
  private List<Integer> weights;
  private List<Integer> values;

  public Problem() {}

  public Problem(Integer capacity, List<Integer> weights, List<Integer> values) {
    this.capacity = capacity;
    this.weights = weights;
    this.values = values;
  }

  /** @return the capacity */
  public Integer getCapacity() {
    return capacity;
  }

  /** @param capacity the capacity to set */
  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  /** @return the weights */
  public List<Integer> getWeights() {
    return weights;
  }

  /** @param weights the weights to set */
  public void setWeights(List<Integer> weights) {
    this.weights = weights;
  }

  /** @return the values */
  public List<Integer> getValues() {
    return values;
  }

  /** @param values the values to set */
  public void setValues(List<Integer> values) {
    this.values = values;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("Problem [capacity=")
        .append(capacity)
        .append(", weights=")
        .append(weights)
        .append(", values=")
        .append(values)
        .append("]");
    return builder.toString();
  }
}
