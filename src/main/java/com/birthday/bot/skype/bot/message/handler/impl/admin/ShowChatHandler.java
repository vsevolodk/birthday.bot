package com.birthday.bot.skype.bot.message.handler.impl.admin;

import com.birthday.bot.db.NitriteHolder;
import com.birthday.bot.skype.chat.ChatForBDayState;
import com.birthday.bot.skype.contact.Contact;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;
import org.dizitart.no2.objects.ObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowChatHandler extends AbstractAdminHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShowChatHandler.class);

  private Message response;

  @Override
  public void handle(MessageReceivedEvent messageReceivedEvent) {
    final ObjectRepository<ChatForBDayState> repository =
            NitriteHolder.getInstance().getRepository(ChatForBDayState.class);

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Current chats: \n");
    for (ChatForBDayState chatForBDayState : repository.find()) {
      stringBuilder.append(chatForBDayState).append("\n");
    }

    response = Message.fromHtml(stringBuilder.toString());
    super.handle(messageReceivedEvent);
  }

  @Override
  protected Message getMessage(MessageReceivedEvent messageReceivedEvent) {
    return response;
  }
}
