package org.optimization.service.model;

import java.io.Serializable;
import java.time.Instant;

public class Timestamps implements Serializable {
  /** */
  private static final long serialVersionUID = 1L;

  private Instant submitted;
  private Instant started;
  private Instant completed;

  /** @return the submitted */
  public Instant getSubmitted() {
    return submitted;
  }

  /** @param submitted the submitted to set */
  public void setSubmitted(Instant submitted) {
    this.submitted = submitted;
  }

  /** @return the started */
  public Instant getStarted() {
    return started;
  }

  /** @param started the started to set */
  public void setStarted(Instant started) {
    this.started = started;
  }

  /** @return the completed */
  public Instant getCompleted() {
    return completed;
  }

  /** @param completed the completed to set */
  public void setCompleted(Instant completed) {
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
    private Instant submitted;
    private Instant started;
    private Instant completed;

    public Builder submitted(Instant submitted) {
      this.submitted = submitted;
      return this;
    }

    public Builder started(Instant started) {
      this.started = started;
      return this;
    }

    public Builder completed(Instant completed) {
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
