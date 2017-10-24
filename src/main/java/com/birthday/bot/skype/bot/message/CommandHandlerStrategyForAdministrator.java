package com.birthday.bot.skype.bot.message;

import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.*;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static com.birthday.bot.skype.bot.message.CommandsConstants.*;

public class CommandHandlerStrategyForAdministrator {

  private static final Map<String, CommandHandler> strategies = new HashMap<String, CommandHandler>(1, 1) {{
    put(HELP, new HelpHandler());
    put(STATUS, new StatusHandler());
    put(PING, new PingHandler());
    put(CREATE_CHAT, new CreateChatsHandler());
    put(ADD_CONTACT, new AddContactHandler());
    put(RM_CONTACT, new RemoveContactHandler());
    put(SHOW_CONTACTS, new ShowContactHandler());
    put(SHOW_CHATS, new ShowChatHandler());
    put(ADD_HISTORY_GIFT, new AddHistoryGiftForContactHandler());
  }};

  public static void handle(final MessageReceivedEvent event) {
    final String command = getCommand(event);
    final CommandHandler commandHandler = strategies.get(command);
    if (commandHandler != null) {
      commandHandler.handle(event);
    }
  }

  private static String getCommand(final MessageReceivedEvent event) {
    final String mes = event.getMessage().getContent().asPlaintext();
    final StringTokenizer tokenizer = new StringTokenizer(mes, " ");
    final String command = tokenizer.nextToken().toLowerCase();
    return command;
  }
}