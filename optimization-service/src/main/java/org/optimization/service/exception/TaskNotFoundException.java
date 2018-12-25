package org.optimization.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * 
 * Exception definition when task is not found.
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "task not found")
public class TaskNotFoundException extends RuntimeException {

  /** */
  private static final long serialVersionUID = 1L;
}
