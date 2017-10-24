package com.birthday.bot.skype.bot.message.handler.impl.admin;

import com.birthday.bot.skype.bot.job.BirthdayChatCreatorJob;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateChatsHandler extends AbstractAdminHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreateChatsHandler.class);

  private Message response;

  @Override
  public void handle(MessageReceivedEvent messageReceivedEvent) {
    try {
      new BirthdayChatCreatorJob().execute(null);
    } catch (JobExecutionException e) {
      LOGGER.error(this.getClass().getName() + " failed", e);
      response = Message.fromHtml("I could not create chats. See logs");
      super.handle(messageReceivedEvent);
      return;
    }
    response = Message.fromHtml("I created chats for comming soon birtdays");
    super.handle(messageReceivedEvent);
  }

  @Override
  protected Message getMessage(MessageReceivedEvent messageReceivedEvent) {
    return response;
  }
}
