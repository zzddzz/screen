package com.east.sword.screen.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.east.sword.screen.entity.Screen;
import com.east.sword.screen.job.msg.IMsgService;
import com.east.sword.screen.service.IScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时器多线程重写
 *
 * @CreateDate 22:17 2020/2/19.
 * @Author ZZD
 */
@Slf4j
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    private ScheduledTaskRegistrar taskRegistrar;
    private Set<ScheduledFuture<?>> scheduledFutures = null;
    private Map<String, ScheduledFuture<?>> taskFutures = new ConcurrentHashMap<>();
    private Map<String, String> taskCrons = new ConcurrentHashMap<>();

    @Autowired
    private IScreenService screenService;

    @Autowired
    private IMsgService sendMsgService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        //设定一个长度10的定时任务线程池
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(20));

        this.taskRegistrar = taskRegistrar;

        EntityWrapper<Screen> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("enable", "1");
        List<Screen> screenList = screenService.selectList(entityWrapper);

        //初始化定时任务
        for (Screen screen : screenList) {
            this.addTriggerTask(
                    String.valueOf(screen.getNo()),
                    new TriggerTask(
                            () -> sendMsgService.sendMsg(screen),
                            new CronTrigger(screen.getCron())
                    ),
                    screen.getCron()
            );
        }
    }


    public Set<ScheduledFuture<?>> getScheduledFutures() {
        if (scheduledFutures == null) {
            try {
                // spring版本不同选用不同字段scheduledFutures
                scheduledFutures = (Set<ScheduledFuture<?>>) BeanUtils.getProperty(taskRegistrar, "scheduledTasks");
            } catch (NoSuchFieldException e) {
                throw new SchedulingException("not found scheduledFutures field.");
            }
        }
        return scheduledFutures;
    }

    /**
     * 获取任务cron
     *
     * @param taskId
     * @return
     */
    public String getTaskCron(String taskId) {
        return taskCrons.get(taskId);
    }

    /**
     * 获取缓存的cron
     *
     * @return
     */
    public Map<String, String> getCacheCronMap() {
        return taskCrons;
    }

    /**
     * 添加任务
     */
    public void addTriggerTask(String taskId, TriggerTask triggerTask, String cron) {
        if (taskFutures.containsKey(taskId)) {
            throw new SchedulingException("the taskId[" + taskId + "] was added.");
        }
        TaskScheduler scheduler = taskRegistrar.getScheduler();
        ScheduledFuture<?> future = scheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
        getScheduledFutures().add(future);
        taskFutures.put(taskId, future);
        taskCrons.put(taskId, cron);
    }


    /**
     * 取消任务
     */
    public void cancelTriggerTask(String taskId) {
        ScheduledFuture<?> future = taskFutures.get(taskId);
        if (future != null) {
            future.cancel(true);
        }
        taskFutures.remove(taskId);
        getScheduledFutures().remove(future);
    }

    /**
     * 重置任务
     */
    public void resetTriggerTask(String taskId, TriggerTask triggerTask, String cron) {
        cancelTriggerTask(taskId);
        addTriggerTask(taskId, triggerTask, cron);
    }

    /**
     * 任务编号
     */
    public Set<String> taskIds() {
        return taskFutures.keySet();
    }

    /**
     * 是否存在任务
     */
    public boolean hasTask(String taskId) {
        return this.taskFutures.containsKey(taskId);
    }

    /**
     * 任务调度是否已经初始化完成
     */
    public boolean inited() {
        return this.taskRegistrar != null && this.taskRegistrar.getScheduler() != null;
    }


}

