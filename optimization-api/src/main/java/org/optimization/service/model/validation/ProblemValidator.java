package org.optimization.service.model.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.optimization.service.model.Problem;
import org.optimization.service.model.validation.ProblemValidator.Issue;

/** Validates if problem is correct KnapsackProblem. */
public class ProblemValidator implements Validate<Issue> {

  public enum Issue {
    WEIGHT_AND_VALUE_ARE_NOT_OF_SAME_SIZE,
    WEIGHT_CANNOT_BE_NEGATIVE,
    CAPACITY_CANNOT_BE_NEGATIVE_OR_NULL;
  }

  private List<Issue> issues = new ArrayList<>();
  private final Problem problem;

  private ProblemValidator(Problem problem) {
    this.problem = problem;
  }

  public static ProblemValidator create(Problem problem) {
    Objects.requireNonNull(problem);
    return new ProblemValidator(problem);
  }

  @Override
  public boolean isValid() {

    Predicate<Problem> capacityNotNullAndNegative =
        p -> p.getCapacity() != null && p.getCapacity() > 0;
    Predicate<Problem> weightAndValueOfSameSize =
        p ->
            p.getWeights() != null
                && p.getValues() != null
                && (p.getWeights().size() == p.getValues().size());
    Predicate<Problem> weightCannotBeNegative =
        p -> p.getWeights().stream().filter(w -> w < 0).count() == 0;

    if (!capacityNotNullAndNegative.test(problem))
      issues.add(Issue.CAPACITY_CANNOT_BE_NEGATIVE_OR_NULL);
    if (!weightAndValueOfSameSize.test(problem))
      issues.add(Issue.WEIGHT_AND_VALUE_ARE_NOT_OF_SAME_SIZE);
    if (!weightCannotBeNegative.test(problem)) issues.add(Issue.WEIGHT_CANNOT_BE_NEGATIVE);

    return issues.isEmpty();
  }

  @Override
  public List<Issue> issues() {
    return issues;
  }
}
