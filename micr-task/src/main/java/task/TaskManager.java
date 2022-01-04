package task;


import com.lwm.api.service.IncomeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lwm1435@163.com
 * @date 2021-12-30 17:41
 * @description 定时任务类
 */
@Component
public class TaskManager {

    @DubboReference(interfaceClass = IncomeService.class, version = "1.0")
    private IncomeService incomeService;

    /**
     * 定时任务处理收益计划
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void handleIncomePlan(){
        System.out.println("收益计划执行");
        incomeService.generateIncomePlan();
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void handleIncomeBack(){
        System.out.println("收益返还执行");
        incomeService.incomeBack();
    }
}
