package com.birthday.bot.skype.bot.message.handler.impl.admin;

import com.birthday.bot.db.NitriteHolder;
import com.birthday.bot.skype.Reloader;
import com.birthday.bot.skype.chat.ChatRepository;
import com.birthday.bot.skype.contact.Contact;
import com.birthday.bot.skype.contact.ContactRepository;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;
import org.dizitart.no2.objects.ObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class RemoveContactHandler extends AbstractAdminHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoveContactHandler.class);

  private Message response;

  @Override
  public void handle(MessageReceivedEvent messageReceivedEvent) {
    try {
      final List<String> parameters = getParameters(messageReceivedEvent);
      final String contactSkype = parameters.get(0);

      final boolean result = ChatRepository.getInstance().removeContactFromAllChatsAndHisChat(contactSkype);
      ContactRepository.getInstance().removeContact(contactSkype);

      if (!result) {
        response = Message.fromHtml("I cannot remove contact from all chats from storage");
        super.handle(messageReceivedEvent);
        return;
      }

    } catch (Exception e) {
      LOGGER.error("Error during removing contact", e);
      response = Message.fromHtml("I cannot remove contact from storage");
      super.handle(messageReceivedEvent);
      return;
    }

    response = Message.fromHtml("I removed contact from storage");
    super.handle(messageReceivedEvent);
  }

  @Override
  protected Message getMessage(MessageReceivedEvent messageReceivedEvent) {
    return response;
  }
}
