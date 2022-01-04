package task;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lwm1435@163.com
 * @date 2021-12-30 17:39
 * @description
 */
@EnableScheduling
@EnableDubbo
@SpringBootApplication
public class TaskApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TaskApplication.class, args);
        TaskManager taskManager = run.getBean("taskManager", TaskManager.class);
        taskManager.handleIncomeBack();
    }
}
