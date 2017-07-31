package com.birthday.bot.skype.bot.job;

import com.birthday.bot.skype.holder.SkypeHolder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class UpdateTokenJob implements Job {

    public UpdateTokenJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("UpdateTokenJob executing...");
        SkypeHolder.updateToken();
    }
}
