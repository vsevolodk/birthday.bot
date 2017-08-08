package com.birthday.bot.skype.job;

import com.birthday.bot.skype.bot.job.BirthdayChatCreatorJob;
import com.birthday.bot.skype.bot.job.PingChatJob;
import com.birthday.bot.skype.bot.job.SkypeReloader;
import com.birthday.bot.skype.settings.loader.BirthdayBotSettings;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class BotJobManager {

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

        int skypeLiveInMinutesBeforeReload = BirthdayBotSettings.getInstance().getConfiguration()
                .getSkypeLiveInMinutesBeforeReload()
                .intValue();

        String schedulingTime = BirthdayBotSettings.getInstance().getConfiguration()
                .getSchedulingTime();
        String[] time = schedulingTime.split("\\:");
        int hour = Integer.valueOf(time[0]);
        int minute = Integer.valueOf(time[1]);

        scheduleSkypeReloadJob(scheduler, skypeLiveInMinutesBeforeReload);
        scheduleJob(scheduler, BirthdayChatCreatorJob.class, "birthdayChatCreator", hour, minute);
        scheduleJob(scheduler, PingChatJob.class, "pingRunner", hour, minute + 5);
    }

    private static void scheduleSkypeReloadJob(
            final Scheduler scheduler,
            int repeatMinutes
    ) throws SchedulerException {
        JobDetail reLoginJob = newJob(SkypeReloader.class)
                .withIdentity("skypeReloadJob", "mainGroup")
                .build();

        Trigger reLoginTrigger = newTrigger()
                .withIdentity("skypeReloadTrigger", "mainGroup")
                .startNow()
                .withSchedule(
                        simpleSchedule()
                                .withIntervalInMinutes(repeatMinutes)
                                .repeatForever()
                )
                .build();

        scheduler.scheduleJob(reLoginJob, reLoginTrigger);
    }

    private static void scheduleJob(
            final Scheduler scheduler,
            final Class<? extends Job> jobClass,
            final String name,
            int hour,
            int minute
    ) throws SchedulerException {
        JobDetail pingRunnerJob = newJob(jobClass)
                .withIdentity(name + "Job", "mainGroup")
                .build();

        Trigger pingRunnerTrigger = newTrigger()
                .withIdentity(name + "Trigger", "mainGroup")
                .startNow()
                .withSchedule(dailyAtHourAndMinute(hour, minute))
                .build();

        scheduler.scheduleJob(pingRunnerJob, pingRunnerTrigger);
    }
}
