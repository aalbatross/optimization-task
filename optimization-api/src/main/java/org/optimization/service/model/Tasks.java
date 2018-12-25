package org.optimization.service.model;

import java.util.ArrayList;
import java.util.List;

public class Tasks {
  private List<String> submitted = new ArrayList<>();
  private List<String> started = new ArrayList<>();
  private List<String> completed = new ArrayList<>();
  /** @return the submitted */
  public List<String> getSubmitted() {
    return submitted;
  }
  /** @param submitted the submitted to set */
  public void setSubmitted(List<String> submitted) {
    this.submitted = submitted;
  }
  /** @return the started */
  public List<String> getStarted() {
    return started;
  }
  /** @param started the started to set */
  public void setStarted(List<String> started) {
    this.started = started;
  }
  /** @return the completed */
  public List<String> getCompleted() {
    return completed;
  }
  /** @param completed the completed to set */
  public void setCompleted(List<String> completed) {
    this.completed = completed;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("Tasks [submitted=")
        .append(submitted)
        .append(", started=")
        .append(started)
        .append(", completed=")
        .append(completed)
        .append("]");
    return builder.toString();
  }
}
