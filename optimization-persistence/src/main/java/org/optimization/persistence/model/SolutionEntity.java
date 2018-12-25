package org.optimization.persistence.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "Solution")
@Table(name = "solution")
public class SolutionEntity {

  public SolutionEntity() {}

  public SolutionEntity(List<Integer> items, Long time) {
    super();
    this.items = items;
    this.time = time;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ElementCollection private List<Integer> items;
  private Long time;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "problem_id")
  private ProblemEntity problem;

  /** @return the id */
  public Long getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(Long id) {
    this.id = id;
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

  /** @return the problem */
  public ProblemEntity getProblem() {
    return problem;
  }

  /** @param problem the problem to set */
  public void setProblem(ProblemEntity problem) {
    this.problem = problem;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder2 = new StringBuilder();
    builder2
        .append("Solution [id=")
        .append(id)
        .append(", items=")
        .append(items)
        .append(", time=")
        .append(time)
        .append(", problem=")
        .append(problem)
        .append("]");
    return builder2.toString();
  }

  public static class Builder {
    private Long id;
    private List<Integer> items;
    private Long time;
    private ProblemEntity problem;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder items(List<Integer> items) {
      this.items = items;
      return this;
    }

    public Builder time(Long time) {
      this.time = time;
      return this;
    }

    public Builder problem(ProblemEntity problem) {
      this.problem = problem;
      return this;
    }

    public SolutionEntity build() {
      return new SolutionEntity(this);
    }
  }

  private SolutionEntity(Builder builder) {
    this.id = builder.id;
    this.items = builder.items;
    this.time = builder.time;
    this.problem = builder.problem;
  }
}
