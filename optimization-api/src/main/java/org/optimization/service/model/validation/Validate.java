package org.optimization.service.model.validation;

import java.util.List;

public interface Validate<T> {

  public List<T> issues();

  public boolean isValid();
}
