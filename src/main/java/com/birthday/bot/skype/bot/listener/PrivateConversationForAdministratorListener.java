package com.birthday.bot.skype.bot.listener;

import com.birthday.bot.skype.bot.message.CommandHandlerStrategyForAdministrator;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.IndividualChat;
import com.samczsun.skype4j.chat.messages.ReceivedMessage;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;

public class PrivateConversationForAdministratorListener implements Listener {

  private static final CommandHandlerStrategyForAdministrator HANDLER_STRATEGY =
          new CommandHandlerStrategyForAdministrator();

  @EventHandler
  public void onMessage(MessageReceivedEvent e) {
    final Chat chat = e.getMessage().getChat();
    if (chat instanceof IndividualChat) {

      ReceivedMessage message = e.getMessage();
      Message content = message.getContent();
      String plaintext = content.asPlaintext();

      if (plaintext.startsWith("\\")) {
        HANDLER_STRATEGY.handle(e);
      }
    }
  }
}