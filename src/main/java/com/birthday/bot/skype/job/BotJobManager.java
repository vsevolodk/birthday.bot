package com.birthday.bot.skype.job;

import com.birthday.bot.skype.bot.job.BirthdayChatCreatorJob;
import com.birthday.bot.skype.bot.job.PingChatJob;
import com.birthday.bot.skype.bot.job.UpdateTokenJob;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.DateBuilder.tomorrowAt;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class BotJobManager {

    private static final int HOUR_PM_START = 10;

    public static void initScheduler() {
        try {
            System.out.println("Initialization of scheduling...");
            internalInit();
        } catch (SchedulerException e) {
            System.out.println("Scheduler initialization is failed");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void internalInit() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        Scheduler scheduler = schedulerFactory.getScheduler();

        scheduler.start();

        scheduleReLoginJob(scheduler, 0);
        scheduleJob(scheduler, BirthdayChatCreatorJob.class, "birthdayChatCreator", 1);
        scheduleJob(scheduler, PingChatJob.class, "pingRunner", 2);
    }

    private static void scheduleReLoginJob(final Scheduler scheduler, int minute) throws SchedulerException {
        JobDetail reLoginJob = newJob(UpdateTokenJob.class)
                .withIdentity("updateTokenJob", "mainGroup")
                .build();

        Trigger reLoginTrigger = newTrigger()
                .withIdentity("reLoginTrigger", "mainGroup")
                .startAt(tomorrowAt(HOUR_PM_START, 0, 0))
                .withSchedule(dailyAtHourAndMinute(HOUR_PM_START, minute))
                .build();

        scheduler.scheduleJob(reLoginJob, reLoginTrigger);
    }

    private static void scheduleJob(
            final Scheduler scheduler,
            final Class<? extends Job> jobClass,
            final String name,
            int minute
    ) throws SchedulerException {
        JobDetail pingRunnerJob = newJob(jobClass)
                .withIdentity(name + "Job", "mainGroup")
                .build();

        Trigger pingRunnerTrigger = newTrigger()
                .withIdentity(name + "Trigger", "mainGroup")
                .startNow()
                .withSchedule(dailyAtHourAndMinute(HOUR_PM_START, minute))
                .build();

        scheduler.scheduleJob(pingRunnerJob, pingRunnerTrigger);
    }
}
