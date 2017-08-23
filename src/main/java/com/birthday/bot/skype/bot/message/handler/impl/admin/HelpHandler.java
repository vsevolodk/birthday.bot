package com.birthday.bot.skype.bot.message.handler.impl.admin;

import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.contact.ContactRepository;
import com.birthday.bot.skype.contact.ContactWithBDay;
import com.samczsun.skype4j.chat.IndividualChat;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.participants.Participant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelpHandler extends AbstractAdminCommand {

  private static final Logger LOGGER = LoggerFactory.getLogger(HelpHandler.class);

  private static final Message helpMessage = Message.fromHtml(
          "0. \\bHelp - command for status of the bot \n"
            + "1. \\bStatus - command for adding new contact (WIP) \n"
            + "2. \\bAddContact - command for adding new contact (WIP) \n"
            + "3. \\bRemoveContact - command for removing new contact (WIP)"
  );

  @Override
  protected Message getMessage() {
    return helpMessage;
  }
}