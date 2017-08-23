package com.birthday.bot.skype.bot.message.handler.impl.admin;

import com.birthday.bot.skype.holder.SchedulerHolder;
import com.samczsun.skype4j.formatting.Message;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Set;

public class StatusHandler extends AbstractAdminCommand {

  private static final Logger LOGGER = LoggerFactory.getLogger(StatusHandler.class);

  @Override
  protected Message getMessage() {
    try {
      StringBuilder mes = new StringBuilder();
      mes.append("Status response:\n");

      Scheduler scheduler = SchedulerHolder.getScheduler();
      mes.append(" Scheduler triggers:\n");
      Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(GroupMatcher.anyGroup());
      for (TriggerKey triggerKey : triggerKeys) {
        mes.append("  triggerGroup=").append(triggerKey.getGroup()).append("\n");
        mes.append("  triggerName=").append(triggerKey.getName()).append("\n");

        Trigger trigger = scheduler.getTrigger(triggerKey);
        Date nextFireTime = trigger.getNextFireTime();
        Date endTime = trigger.getEndTime();
        Date previousFireTime = trigger.getPreviousFireTime();

        mes.append("    nextFireTime=").append(nextFireTime).append("\n");
        mes.append("    previousFireTime=").append(previousFireTime).append("\n");
        mes.append("    endTime=").append(endTime).append("\n");

      }


      return Message.fromHtml(mes.toString());
    } catch (SchedulerException e) {
      LOGGER.error("Status command failed", e);
      return null;
    }
  }
}
