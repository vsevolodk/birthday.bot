package com.birthday.bot.skype.bot.message.handler.impl.group;

import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.AbstractAdminHandler;
import com.birthday.bot.skype.chat.ChatForBDay;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelpHandler extends CommandHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(HelpHandler.class);

  private static final Message helpMessage = Message.fromHtml(
          "0. \\bOption - command for adding gift option for current chat \n"
            + "1. \\bShow - command for display current gift options for current chat \n"
            + "2. \\bDone - command for close activity for current chat \n"
            + "3. \\bShowHistoryGift - command for show history of gifts \n"
            + "4. \\bAddHistoryGift year;gift - command for add gift for year \n"

  );

  @Override
  public void handle(MessageReceivedEvent messageReceivedEvent) {
    final ChatForBDay chatForBDay = getChatForBDay(messageReceivedEvent);
    try {
      chatForBDay.sendMessage(helpMessage);
    } catch (ConnectionException e) {
      LOGGER.error("Error during group help command", e);
    }
  }
}