package org.optimization.persistence.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Task")
@Table(name = "task")
public class TaskEntity {

  public TaskEntity() {}

  public enum Status {
    SUBMITTED,
    STARTED,
    COMPLETED
  }

  @Embeddable
  public static class Timestamps {

    private Date submitted;
    private Date started;
    private Date completed;

    public Timestamps() {}

    public Timestamps(Date submitted, Date started, Date completed) {
      this.submitted = submitted;
      this.started = started;
      this.completed = completed;
    }

    /** @return the submitted */
    public Date getSubmitted() {
      return submitted;
    }

    /** @param submitted the submitted to set */
    public void setSubmitted(Date submitted) {
      this.submitted = submitted;
    }

    /** @return the started */
    public Date getStarted() {
      return started;
    }

    /** @param started the started to set */
    public void setStarted(Date started) {
      this.started = started;
    }

    /** @return the completed */
    public Date getCompleted() {
      return completed;
    }

    /** @param completed the completed to set */
    public void setCompleted(Date completed) {
      this.completed = completed;
    }

    /*
     * (non-Javadoc)
     *
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
  }

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Enumerated(EnumType.STRING)
  private Status status;

  private Timestamps timestamps;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "problem_id")
  private ProblemEntity problem;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "solution_id")
  private SolutionEntity solution;

  /** @return the task */
  public String getId() {
    return id;
  }

  /** @param task the task to set */
  public void setId(String task) {
    this.id = task;
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

  /** @return the problem */
  public ProblemEntity getProblem() {
    return problem;
  }

  /** @param problem the problem to set */
  public void setProblem(ProblemEntity problem) {
    this.problem = problem;
  }

  /** @return the solution */
  public SolutionEntity getSolution() {
    return solution;
  }

  /** @param solution the solution to set */
  public void setSolution(SolutionEntity solution) {
    this.solution = solution;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("Task [task=")
        .append(id)
        .append(", status=")
        .append(status)
        .append(", timestamps=")
        .append(timestamps)
        .append(", problem=")
        .append(problem)
        .append("]");
    return builder.toString();
  }

  public static class Builder {
    private String id;
    private Status status;
    private Timestamps timestamps;
    private ProblemEntity problem;
    private SolutionEntity solution;

    public Builder id(String id) {
      this.id = id;
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

    public Builder problem(ProblemEntity problem) {
      this.problem = problem;
      return this;
    }

    public Builder solution(SolutionEntity solution) {
      this.solution = solution;
      return this;
    }

    public TaskEntity build() {
      return new TaskEntity(this);
    }
  }

  private TaskEntity(Builder builder) {
    this.id = builder.id;
    this.status = builder.status;
    this.timestamps = builder.timestamps;
    this.problem = builder.problem;
    this.solution = builder.solution;
  }
}
