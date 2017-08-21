package com.birthday.bot.skype.bot.job;

import com.birthday.bot.skype.Reloader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkypeReloader implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkypeReloader.class);

    public SkypeReloader() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("SkypeReloader executing...");
        Reloader.reload();
    }
}
