package com.birthday.bot.skype.bot.message.handler.impl.admin;

import com.birthday.bot.db.NitriteHolder;
import com.birthday.bot.skype.contact.Contact;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;
import org.dizitart.no2.objects.ObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowContactHandler extends AbstractAdminHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShowContactHandler.class);

  private Message response;

  @Override
  public void handle(MessageReceivedEvent messageReceivedEvent) {
    final ObjectRepository<Contact> repository =
            NitriteHolder.getInstance().getRepository(Contact.class);

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Current contacts; \n");
    for (Contact contact : repository.find()) {
      stringBuilder.append(contact).append("\n");
    }

    response = Message.fromHtml(stringBuilder.toString());
    super.handle(messageReceivedEvent);
  }

  @Override
  protected Message getMessage(MessageReceivedEvent messageReceivedEvent) {
    return response;
  }
}
