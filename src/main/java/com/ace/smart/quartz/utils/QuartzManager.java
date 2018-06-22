package com.ace.smart.quartz.utils;

import com.ace.smart.entity.SchedulerJob;
import com.ace.smart.entity.SysTask;
import jdk.nashorn.internal.scripts.JO;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class QuartzManager {

    public static final Logger LOGGER = LoggerFactory.getLogger(QuartzManager.class);

    @Autowired
    private Scheduler scheduler;

    /**
     * @desc 将任务加入到 JobDetail 中 开始执行
     * @author zzh
     * @date 2018/6/11 15:12
     * @param
     * @return
     */
    public void addJob(SysTask task) {
        try{
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类

            Class <? extends Job> jobClass = (Class<? extends Job>) (Class.forName(task.getBeanClass()).newInstance().getClass());
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(task.getJobName(),task.getJobGroup())
                    .build();

            // 定义调度触发规则
            // 使用cornTrigger 规则
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getJobName(),task.getJobGroup())
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression()))
                    .startNow().build();

            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail,trigger);

            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc 获取所有计划中的任务列表
     * @author zzh
     * @date 2018/6/11 15:22
     * @param
     * @return
     */
    public List<SysTask> getAllJob() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<SysTask> sysTasks = new ArrayList<SysTask>();

        for (JobKey jobKey : jobKeys) {
            List<?extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                SysTask task = new SysTask();
                task.setJobName(jobKey.getName());
                task.setJobGroup(jobKey.getGroup());
                task.setDescription("触发器:"+trigger.getKey());

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                task.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    task.setCronExpression(cronExpression);
                }
                sysTasks.add(task);
            }
        }
        return sysTasks;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public List<SysTask> getRunningJob() throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<SysTask> jobList = new ArrayList<SysTask>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            SysTask job = new SysTask();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void pauseJob(SysTask scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void resumeJob(SysTask scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(SysTask scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);

    }

    /**
     * 立即执行job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void runAJobNow(SysTask scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void updateJobCron(SysTask scheduleJob) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        scheduler.rescheduleJob(triggerKey, trigger);
    }
}
