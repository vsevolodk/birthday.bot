package com.birthday.bot.skype.job;

import com.birthday.bot.skype.bot.job.ReLoginJob;
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
        scheduleJob(scheduler, "birthdayChatCreator", 1);
        scheduleJob(scheduler, "pingRunner", 1);
    }

    private static void scheduleReLoginJob(final Scheduler scheduler, int minute) throws SchedulerException {
        JobDetail reLoginJob = newJob(ReLoginJob.class)
                .withIdentity("reLoginJob", "mainGroup")
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
            final String name,
            int minute
    ) throws SchedulerException {
        JobDetail pingRunnerJob = newJob(ReLoginJob.class)
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
