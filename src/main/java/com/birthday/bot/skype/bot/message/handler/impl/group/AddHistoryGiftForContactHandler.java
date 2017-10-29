package com.birthday.bot.skype.bot.message.handler.impl.group;

import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.contact.ContactRepository;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AddHistoryGiftForContactHandler extends CommandHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddHistoryGiftForContactHandler.class);

  private Message response;

  @Override
  public void handle(MessageReceivedEvent messageReceivedEvent) {
    try {
      final List<String> parameters = getParameters(messageReceivedEvent);
      final String[] params = parameters.get(0).split(";");
      final String skypeLogin = getSkypeLogin(messageReceivedEvent);
      final Integer year = Integer.valueOf(params[0]);
      final String gift = params[1];

      ContactRepository.getInstance().addHistoryGiftForContact(skypeLogin, year, gift);

    } catch (Exception e) {
      LOGGER.error("Error during adding history gift", e);
      response = Message.fromHtml("I cannot add history gift for contact");
      sendResponse(messageReceivedEvent, response);
      return;
    }

    response = Message.fromHtml("I added history gift for contact");
    sendResponse(messageReceivedEvent, response);
  }
}
