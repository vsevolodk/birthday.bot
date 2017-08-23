package com.birthday.bot.skype.holder;

import org.quartz.Scheduler;

public class SchedulerHolder {

  private static Scheduler scheduler;

  public static void init(final Scheduler pScheduler) {
    synchronized (SchedulerHolder.class) {
      scheduler = pScheduler;
    }
  }

  public static Scheduler getScheduler() {
    return scheduler;
  }
}
