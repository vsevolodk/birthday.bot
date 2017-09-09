package com.birthday.bot.skype.bot.message.handler.impl.admin;

import com.birthday.bot.skype.bot.job.PingChatJob;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingHandler extends AbstractAdminCommand {

  private static final Logger LOGGER = LoggerFactory.getLogger(PingHandler.class);

  private Message response;

  @Override
  public void handle(MessageReceivedEvent messageReceivedEvent) {
    try {
      new PingChatJob().execute(null);
    } catch (JobExecutionException e) {
      LOGGER.error(this.getClass().getName() + " failed", e);
      response = Message.fromHtml("I could not ping due to internal error. See logs");
    }
    response = Message.fromHtml("I pinged all current chats");
    super.handle(messageReceivedEvent);
  }

  @Override
  protected Message getMessage() {
    return response;
  }
}
