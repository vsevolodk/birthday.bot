package com.birthday.bot.skype.bot.message;

import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.CreateChatsHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.HelpHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.PingHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.StatusHandler;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static com.birthday.bot.skype.bot.message.CommandsConstants.HELP;
import static com.birthday.bot.skype.bot.message.CommandsConstants.STATUS;
import static com.birthday.bot.skype.bot.message.CommandsConstants.PING;
import static com.birthday.bot.skype.bot.message.CommandsConstants.CREATE;

public class CommandHandlerStrategyForAdministrator {

  private static final Map<String, CommandHandler> strategies = new HashMap<String, CommandHandler>(1, 1) {{
    put(HELP, new HelpHandler());
    put(STATUS, new StatusHandler());
    put(PING, new PingHandler());
    put(CREATE, new CreateChatsHandler());
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