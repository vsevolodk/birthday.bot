package com.birthday.bot.skype.bot.message.handler.impl.group;

import com.birthday.bot.db.NitriteHolder;
import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.contact.Contact;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;
import org.dizitart.no2.objects.ObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class ShowHistoryGiftHandler extends CommandHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShowHistoryGiftHandler.class);

  private Message response;

  @Override
  public void handle(MessageReceivedEvent messageReceivedEvent) {
    final ObjectRepository<Contact> repository =
            NitriteHolder.getInstance().getRepository(Contact.class);
    final Contact contact =
            repository.find(eq("skype", getSkypeLogin(messageReceivedEvent))).iterator().next();

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Gift history: \n");
    stringBuilder.append("year gift");
    for (Map.Entry<Integer, String> entry : contact.getHistoryMap().entrySet()) {
      final Integer year = entry.getKey();
      final String gift = entry.getValue();
      stringBuilder
              .append(year).append(" ").append(gift)
              .append("\n");
    }

    response = Message.fromHtml(stringBuilder.toString());
    sendResponse(messageReceivedEvent, response);
  }
}
