package com.birthday.bot.skype.bot.message.handler.impl.admin;

import com.birthday.bot.db.NitriteHolder;
import com.birthday.bot.skype.Reloader;
import com.birthday.bot.skype.chat.ChatRepository;
import com.birthday.bot.skype.contact.Contact;
import com.birthday.bot.skype.contact.ContactRepository;
import com.birthday.bot.skype.contact.ContactRepositoryFactory;
import com.birthday.bot.skype.contact.ContactWithBDay;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;
import org.dizitart.no2.objects.ObjectRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class AddContactHandler extends AbstractAdminHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddContactHandler.class);

  private Message response;

  @Override
  public void handle(MessageReceivedEvent messageReceivedEvent) {
    try {
      final String parameters = getParametersAsString(messageReceivedEvent);
      final String[] params = parameters.split(";");
      final String skypeLogin = params[0];
      final String bDay = params[1];
      final String topicName = params[2];
      final String isAdmin = params[3];

      final Contact newContact = new Contact();
      newContact.setSkype(skypeLogin);
      newContact.setbDay(getDate(bDay));
      newContact.setTopicName(topicName);
      newContact.setAdmin(Boolean.valueOf(isAdmin));

      ContactRepository.getInstance().addUser(newContact);

      final boolean result = ChatRepository.getInstance().addNewContactForAllChats(skypeLogin);
      if (!result) {
        response = Message.fromHtml("I cannot add contact for all chats to storage");
        super.handle(messageReceivedEvent);
        return;
      }

    } catch (Exception e) {
      LOGGER.error("Error during adding new contact", e);
      response = Message.fromHtml("I cannot add contact to storage");
      super.handle(messageReceivedEvent);
      return;
    }

    response = Message.fromHtml("I added contact to storage");
    super.handle(messageReceivedEvent);
    Reloader.reload();
  }

  private Date getDate(String bDay) {
    final String[] split = bDay.split("-");
    return new DateTime(
            Integer.valueOf(split[2]),
            Integer.valueOf(split[1]),
            Integer.valueOf(split[0]),
            0,
            0
    ).toDate();
  }

  @Override
  protected Message getMessage(MessageReceivedEvent messageReceivedEvent) {
    return response;
  }
}
