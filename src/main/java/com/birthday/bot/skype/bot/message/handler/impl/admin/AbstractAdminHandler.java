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

public abstract class AbstractAdminHandler extends CommandHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAdminHandler.class);

  private static final String doNotKnowYouMessage = " sorry, I do not know you.";
  private static final String youAreNotAdminMessage = " sorry, you are not admin.";
  private static final String internalErrorMessage = "sorry, internal error is occurred";

  @Override
  public void handle(MessageReceivedEvent messageReceivedEvent) {
    try {
      IndividualChat chat = (IndividualChat) messageReceivedEvent.getMessage().getChat();
      String username = getUserName(chat);
      ContactWithBDay contactWithBDay = ContactRepository.getInstance().getContactWithBDay(username);
      if (contactWithBDay != null) {
        if (contactWithBDay.isAdmin()) {
          Message message = getMessage(messageReceivedEvent);
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

  private String getUserName(IndividualChat chat) {
    Participant partner = chat.getPartner();
    String id = partner.getId();
    // id is return as 8:nickname, so we cut first 2 chars
    return id.substring(2);
  }

  protected abstract Message getMessage(MessageReceivedEvent messageReceivedEvent);
}
