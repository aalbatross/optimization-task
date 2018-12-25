package org.optimization.persistence.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * 
 * Definition of problem represented in db.
 *
 */
@Entity(name = "Problem")
@Table(name = "problem")
public class ProblemEntity {

  public ProblemEntity() {}

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Integer capacity;
  @ElementCollection private List<Integer> weights;
  @ElementCollection private List<Integer> values;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "solution_id")
  private SolutionEntity solution;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "task_id")
  private List<TaskEntity> task;

  /** @return the id */
  public Long getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(Long id) {
    this.id = id;
  }

  /** @return the capacity */
  public Integer getCapacity() {
    return capacity;
  }

  /** @param capacity the capacity to set */
  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  /** @return the weights */
  public List<Integer> getWeights() {
    return weights;
  }

  /** @param weights the weights to set */
  public void setWeights(List<Integer> weights) {
    this.weights = weights;
  }

  /** @return the values */
  public List<Integer> getValues() {
    return values;
  }

  /** @param values the values to set */
  public void setValues(List<Integer> values) {
    this.values = values;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("Problem [id=")
        .append(id)
        .append(", capacity=")
        .append(capacity)
        .append(", weights=")
        .append(weights)
        .append(", values=")
        .append(values)
        .append("]");
    return builder.toString();
  }

  public static class Builder {
    private Long id;
    private Integer capacity;
    private List<Integer> weights;
    private List<Integer> values;
    private SolutionEntity solution;
    private List<TaskEntity> task;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder capacity(Integer capacity) {
      this.capacity = capacity;
      return this;
    }

    public Builder weights(List<Integer> weights) {
      this.weights = weights;
      return this;
    }

    public Builder values(List<Integer> values) {
      this.values = values;
      return this;
    }

    public Builder solution(SolutionEntity solution) {
      this.solution = solution;
      return this;
    }

    public Builder task(List<TaskEntity> task) {
      this.task = task;
      return this;
    }

    public ProblemEntity build() {
      return new ProblemEntity(this);
    }
  }

  private ProblemEntity(Builder builder) {
    this.id = builder.id;
    this.capacity = builder.capacity;
    this.weights = builder.weights;
    this.values = builder.values;
    this.solution = builder.solution;
    this.task = builder.task;
  }
}
