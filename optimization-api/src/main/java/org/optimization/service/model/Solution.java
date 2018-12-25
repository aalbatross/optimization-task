package org.optimization.service.model;

import java.io.Serializable;

/**
 * 
 * Data structure of the Solution class presented to users when task is completed.
 *
 */
public class Solution implements Serializable {
  /** */
  private static final long serialVersionUID = 1L;

  public Solution() {}

  private String task;
  private Problem problem;
  private Result solution;

  /** @return the task */
  public String getTask() {
    return task;
  }

  /** @param task the task to set */
  public void setTask(String task) {
    this.task = task;
  }

  /** @return the problem */
  public Problem getProblem() {
    return problem;
  }

  /** @param problem the problem to set */
  public void setProblem(Problem problem) {
    this.problem = problem;
  }

  /** @return the solution */
  public Result getSolution() {
    return solution;
  }

  /** @param solution the solution to set */
  public void setSolution(Result solution) {
    this.solution = solution;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("Solution [task=")
        .append(task)
        .append(", problem=")
        .append(problem)
        .append(", solution=")
        .append(solution)
        .append("]");
    return builder.toString();
  }

  public static class Builder {
    private String task;
    private Problem problem;
    private Result solution;

    public Builder task(String task) {
      this.task = task;
      return this;
    }

    public Builder problem(Problem problem) {
      this.problem = problem;
      return this;
    }

    public Builder solution(Result solution) {
      this.solution = solution;
      return this;
    }

    public Solution build() {
      return new Solution(this);
    }
  }

  private Solution(Builder builder) {
    this.task = builder.task;
    this.problem = builder.problem;
    this.solution = builder.solution;
  }
}
