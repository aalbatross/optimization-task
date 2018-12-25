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
import org.optimization.persistence.model.TaskStatusObject;
import org.optimization.persistence.model.TimestampsAssociation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

/**
 * 
 * Testing Data persistence for Task Services.
 *
 */
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
    TaskEntity task2 = taskService.save(task);
    System.out.println("First insert: " + task2);
    Assert.assertEquals(task2.getStatus(), Status.SUBMITTED);
    task2.setStatus(Status.STARTED);
    TaskEntity task3 = taskService.save(task2);
    System.out.println("After update: " + task3);
    Assert.assertEquals(task3.getStatus(), Status.STARTED);
    task3.setStatus(Status.COMPLETED);
    TaskEntity task4 = taskService.save(task3);
    Assert.assertEquals(task4.getStatus(), Status.COMPLETED);
    
  }
  
  /**
   * showTaskStatus test with
   */
  @Test
  public void showTaskStatusTest() {
    showTaskTest(Status.COMPLETED, 3);
    showTaskTest(null, 2); 
  }
  
  
  private void showTaskTest(Status status,int count) {
    taskService.deleteAll();
    for(int i =0;i<count;i++) {
    TaskEntity task =
        new TaskEntity.Builder()
            .status(status)
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
   taskService.save(task);
    }
    Page<TaskStatusObject> tsoPage= taskService.statusOfAllTasks(Pageable.unpaged());
    tsoPage.forEach( tso -> {
      Assert.assertEquals(tso.getStatus(), status);
    });
    Assert.assertEquals(tsoPage.stream().count(),count);
  }
}
