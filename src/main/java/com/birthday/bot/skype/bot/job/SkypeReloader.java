package com.birthday.bot.skype.bot.job;

import com.birthday.bot.skype.Reloader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SkypeReloader implements Job {

    public SkypeReloader() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("SkypeReloader executing...");
        Reloader.reload();
    }
}
