package org.optimization.service.model;

import java.io.Serializable;

/**
 * 
 * Data structure displaying task, there status and timestamps.
 *
 */
public class Task implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  public Task() {}

  public enum Status {
    SUBMITTED,
    STARTED,
    COMPLETED
  }

  private String task;
  private Status status;
  private Timestamps timestamps;

  /** @return the taskId */
  public String getTask() {
    return task;
  }

  /** @param task the taskId to set */
  public void setTask(String task) {
    this.task = task;
  }

  /** @return the status */
  public Status getStatus() {
    return status;
  }

  /** @param status the status to set */
  public void setStatus(Status status) {
    this.status = status;
  }

  /** @return the timestamps */
  public Timestamps getTimestamps() {
    return timestamps;
  }

  /** @param timestamps the timestamps to set */
  public void setTimestamps(Timestamps timestamps) {
    this.timestamps = timestamps;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("Task [task=")
        .append(task)
        .append(", status=")
        .append(status)
        .append(", timestamps=")
        .append(timestamps)
        .append("]");
    return builder.toString();
  }

  public static class Builder {
    private String task;
    private Status status;
    private Timestamps timestamps;

    public Builder task(String task) {
      this.task = task;
      return this;
    }

    public Builder status(Status status) {
      this.status = status;
      return this;
    }

    public Builder timestamps(Timestamps timestamps) {
      this.timestamps = timestamps;
      return this;
    }

    public Task build() {
      return new Task(this);
    }
  }

  private Task(Builder builder) {
    this.task = builder.task;
    this.status = builder.status;
    this.timestamps = builder.timestamps;
  }
}
