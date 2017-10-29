package com.birthday.bot.skype.bot.message;

import com.birthday.bot.skype.bot.message.handler.impl.group.*;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.birthday.bot.skype.bot.message.handler.CommandHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import static com.birthday.bot.skype.bot.message.CommandsConstants.*;

/**
 * Created by Vsevolod Kaimashnikov on 06.03.2016.
 */
public class CommandHandlerStrategy extends AbstractCommandHandlerStrategy {

    private static final Map<String, CommandHandler> strategies = new ConcurrentHashMap<String, CommandHandler>(6, 1) {{
        put(HELP, new HelpHandler());
        put(OPTION, new AddedOption());
        put(DONE, new DeleteChat());
        put(SHOW_OPTIONS, new ShowOptions());
        put(ADD_HISTORY_GIFT, new AddHistoryGiftForContactHandler());
        put(SHOW_HISTORY_GIFT, new ShowHistoryGiftHandler());
    }};


    @Override
    protected Map<String, CommandHandler> getStrategies() {
        return strategies;
    }
}
