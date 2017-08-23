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

public abstract class AbstractAdminCommand extends CommandHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAdminCommand.class);

  private static final String doNotKnowYouMessage = " sorry, I do not know you.";
  private static final String youAreNotAdminMessage = " sorry, you are not admin.";
  private static final String internalErrorMessage = "sorry, internal error is occurred";

  @Override
  public void handle(MessageReceivedEvent messageReceivedEvent) {
    try {
      IndividualChat chat = (IndividualChat) messageReceivedEvent.getMessage().getChat();
      Participant partner = chat.getPartner();
      String username = partner.getId();
      ContactWithBDay contactWithBDay = ContactRepository.getInstance().getContactWithBDay(username);
      if (contactWithBDay != null) {
        if (contactWithBDay.isAdmin()) {
          Message message = getMessage();
          if (message != null) {
            chat.sendMessage(message);
          } else {
            chat.sendMessage(Message.fromHtml(internalErrorMessage));
          }
        } else {
          chat.sendMessage(Message.fromHtml(username + youAreNotAdminMessage));
        }
      } else {
        chat.sendMessage(Message.fromHtml(username + doNotKnowYouMessage));
      }
    } catch (ConnectionException e) {
      LOGGER.error(this.getClass().getName() + " is failed", e);
    }
  }

  protected abstract Message getMessage();
}
