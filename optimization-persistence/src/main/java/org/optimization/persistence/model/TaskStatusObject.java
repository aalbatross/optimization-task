package org.optimization.persistence.model;

import org.optimization.persistence.model.TaskEntity.Status;

/**
 * 
 * Data access object representing the projection of task and status from {@linkplain TaskEntity}.
 *
 */
public class TaskStatusObject {
  private String task;
  private Status status;

  public TaskStatusObject() {}

  public TaskStatusObject(String task, Status status) {
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
