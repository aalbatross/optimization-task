package org.optimization.service.model;

import java.io.Serializable;

/** Datastructure representing the timestamp info of various status of task. */
public class Timestamps implements Serializable {
  /** */
  private static final long serialVersionUID = 1L;

  public Timestamps() {}

  private Long submitted;
  private Long started;
  private Long completed;

  /** @return the submitted */
  public Long getSubmitted() {
    return submitted;
  }

  /** @param submitted the submitted to set */
  public void setSubmitted(Long submitted) {
    this.submitted = submitted;
  }

  /** @return the started */
  public Long getStarted() {
    return started;
  }

  /** @param started the started to set */
  public void setStarted(Long started) {
    this.started = started;
  }

  /** @return the completed */
  public Long getCompleted() {
    return completed;
  }

  /** @param completed the completed to set */
  public void setCompleted(Long completed) {
    this.completed = completed;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("Timestamps [submitted=")
        .append(submitted)
        .append(", started=")
        .append(started)
        .append(", completed=")
        .append(completed)
        .append("]");
    return builder.toString();
  }

  public static class Builder {
    private Long submitted;
    private Long started;
    private Long completed;

    public Builder submitted(Long submitted) {
      this.submitted = submitted;
      return this;
    }

    public Builder started(Long started) {
      this.started = started;
      return this;
    }

    public Builder completed(Long completed) {
      this.completed = completed;
      return this;
    }

    public Timestamps build() {
      return new Timestamps(this);
    }
  }

  private Timestamps(Builder builder) {
    this.submitted = builder.submitted;
    this.started = builder.started;
    this.completed = builder.completed;
  }
}
