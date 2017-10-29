package com.birthday.bot.skype.bot.message;

import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.AddContactHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.AddHistoryGiftForContactHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.CreateChatsHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.HelpHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.PingHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.RemoveContactHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.ShowChatHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.ShowContactHandler;
import com.birthday.bot.skype.bot.message.handler.impl.admin.StatusHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.birthday.bot.skype.bot.message.CommandsConstants.*;

public class CommandHandlerStrategyForAdministrator extends AbstractCommandHandlerStrategy {

  private static final Map<String, CommandHandler> strategies = new ConcurrentHashMap<String, CommandHandler>(9, 1) {{
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


  @Override
  protected Map<String, CommandHandler> getStrategies() {
    return strategies;
  }
}