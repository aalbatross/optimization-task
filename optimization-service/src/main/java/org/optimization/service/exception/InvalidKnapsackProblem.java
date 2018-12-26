package org.optimization.service.exception;

import java.util.List;

import org.optimization.service.model.validation.ProblemValidator.Issue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
  code = HttpStatus.BAD_REQUEST,
  reason =
      "Knapsack Problem is invalid. Rules are: 1. The capacity should be non null and non negative. 2. Weights and values should be of same size. 3. Weight cannot be negative."
)
public class InvalidKnapsackProblem extends RuntimeException {

  /** */
  private static final long serialVersionUID = 1L;

  public InvalidKnapsackProblem(List<Issue> message) {
    super(String.format("Knapsack Problem provided has issues %s", message.toString()));
  }
}
