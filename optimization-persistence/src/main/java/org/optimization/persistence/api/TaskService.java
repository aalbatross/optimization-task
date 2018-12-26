package org.optimization.persistence.api;

import org.optimization.persistence.model.TaskEntity;
import org.optimization.persistence.model.TaskStatusObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Data access layer for task services. */
@Repository
public interface TaskService extends CrudRepository<TaskEntity, String> {

  @Query(
      "select new org.optimization.persistence.model.TaskStatusObject(t.id, t.status) from Task t")
  public Page<TaskStatusObject> statusOfAllTasks(Pageable pageable);
}
