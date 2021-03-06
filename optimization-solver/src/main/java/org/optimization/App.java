package org.optimization;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.optimization.persistence.api.TaskService;
import org.optimization.persistence.model.ProblemEntity;
import org.optimization.persistence.model.SolutionEntity;
import org.optimization.persistence.model.TaskEntity;
import org.optimization.solver.KnapsackSolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class App implements CommandLineRunner {

  @Value("${topic}")
  private String topic;

  @Value("${batchSize}")
  private String batchSize;

  @Value("${bootstrap.servers}")
  private String bootstrapServers;

  @Value("${group.id}")
  private String consumerGroup;

  @Value("${enable.auto.commit}")
  private boolean autoCommit;

  @Value("${key.deserializer}")
  private String keyDeserializer;

  @Value("${value.deserializer}")
  private String valueDeserializer;

  private Properties kafkaProps() {
    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
    return props;
  }

  public static final Logger LOG = LoggerFactory.getLogger(App.class);

  @Autowired TaskService taskService;

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  /**
   * executes the kafka message from the topic in buffer and executes solver {@linkplain
   * KnapsackSolver} on each of them.
   *
   * @param buffer
   */
  @Transactional
  void executeBatch(List<ConsumerRecord<String, String>> buffer) {
    buffer.forEach(
        record -> {
          String taskId = record.value();
          LOG.info("Started {}", taskId);
          TaskEntity task =
              taskService
                  .findById(taskId)
                  .orElseThrow(() -> new RuntimeException(taskId + " task not found or deleted"));

          ProblemEntity problem = task.getProblem();
          int capacity = problem.getCapacity();
          List<Integer> weights = problem.getWeights();
          List<Integer> values = problem.getValues();
          KnapsackSolver solver =
              new KnapsackSolver.Builder()
                  .withCapacity(capacity)
                  .withWeights(weights)
                  .withValues(values)
                  .build();
          task.setStatus(TaskEntity.Status.STARTED);
          task.getTimestamps().setStarted(Instant.now().getEpochSecond());
          task = taskService.save(task);

          List<Integer> indexes = solver.solve();
          task.getTimestamps().setCompleted(Instant.now().getEpochSecond());
          SolutionEntity solution = new SolutionEntity(indexes, solver.timeElapsed());
          task.setSolution(solution);
          task.setStatus(TaskEntity.Status.COMPLETED);
          taskService.save(task);
          LOG.info("Completed {}", taskId);
        });
  }

  /** Opens up Kafka consumer on a particular topic and batch them before executing. */
  @Override
  public void run(String... args) throws Exception {
    LOG.info("Properties: {}", kafkaProps());
    try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaProps())) {
      consumer.subscribe(Arrays.asList(topic));
      final int minBatchSize = Integer.parseInt(batchSize);
      List<ConsumerRecord<String, String>> buffer = new ArrayList<>();

      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
          buffer.add(record);
        }
        if (buffer.size() >= minBatchSize) {
          executeBatch(buffer);
          consumer.commitSync();
          buffer.clear();
        }
      }
    }
  }
}
