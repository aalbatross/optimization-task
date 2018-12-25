package org.optimization.service.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Data structure defining Result of the Knapsack problem.
 *
 */
public class Result implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private List<Integer> items;
  private Long time;

  public Result() {}

  public Result(List<Integer> items, Long time) {
    super();
    this.items = items;
    this.time = time;
  }
  /** @return the items */
  public List<Integer> getItems() {
    return items;
  }
  /** @param items the items to set */
  public void setItems(List<Integer> items) {
    this.items = items;
  }
  /** @return the time */
  public Long getTime() {
    return time;
  }
  /** @param time the time to set */
  public void setTime(Long time) {
    this.time = time;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Result [items=").append(items).append(", time=").append(time).append("]");
    return builder.toString();
  }
}
