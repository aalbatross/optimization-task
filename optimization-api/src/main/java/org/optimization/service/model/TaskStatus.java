package org.optimization.service.model;

import java.io.Serializable;

import org.optimization.service.model.Task.Status;

public class TaskStatus implements Serializable {
  /** */
  private static final long serialVersionUID = 1L;

  private String task;
  private Status status;

  public TaskStatus() {}

  public TaskStatus(String task, Status status) {
    this.task = task;
    this.status = status;
  }

  /** @return the task */
  public String getTask() {
    return task;
  }
  /** @param task the task to set */
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
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("TaskStatus [task=").append(task).append(", status=").append(status).append("]");
    return builder.toString();
  }
}
