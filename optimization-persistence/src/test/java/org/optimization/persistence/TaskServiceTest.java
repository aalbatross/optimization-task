package org.optimization.persistence;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.optimization.persistence.api.TaskService;
import org.optimization.persistence.model.ProblemEntity;
import org.optimization.persistence.model.TaskEntity;
import org.optimization.persistence.model.TaskEntity.Status;
import org.optimization.persistence.model.TimestampsAssociation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskServiceTest {

  @Autowired private TaskService taskService;

  @Test
  public void createTaskTest() {
    taskService.deleteAll();
    TaskEntity task =
        new TaskEntity.Builder()
            .status(Status.SUBMITTED)
            .timestamps(new TimestampsAssociation.Builder().submitted(Instant.now()).build())
            .problem(
                new ProblemEntity.Builder()
                    .capacity(30)
                    .weights(Arrays.asList(1, 2, 3))
                    .values(Arrays.asList(3, 2, 1))
                    .build())
            .build();
    taskService.save(task);

    Assert.assertEquals(taskService.count(), 1);
    taskService.statusOfAllTasks(Pageable.unpaged()).get().forEach(System.out::println);
    ;
  }

  @Test
  public void updateTaskTest() {
    taskService.deleteAll();
    TaskEntity task =
        new TaskEntity.Builder()
            .status(Status.SUBMITTED)
            .timestamps(new TimestampsAssociation.Builder().submitted(Instant.now()).build())
            .problem(
                new ProblemEntity.Builder()
                    .capacity(30)
                    .weights(
                        new ArrayList<Integer>() {
                          {
                            add(1);
                            add(2);
                            add(3);
                          }
                        })
                    .values(
                        new ArrayList<Integer>() {
                          {
                            add(3);
                            add(2);
                            add(1);
                          }
                        })
                    .build())
            .build();
    task = taskService.save(task);
    System.out.println("First insert: " + task);
    // Assert.assertEquals(taskService.count(),1);
    task.setStatus(Status.STARTED);
    task = taskService.save(task);
    System.out.println("After update: " + task);
    // Assert.assertEquals(taskService.count(),1);
    taskService.findAll().forEach(System.out::println);
  }
}
