package com.birthday.bot.skype.run;

import com.birthday.bot.skype.run.mode.ContactsAddingRunMode;
import com.birthday.bot.skype.run.mode.DefaultRunMode;
import com.birthday.bot.skype.run.mode.RunMode;
import com.birthday.bot.skype.run.mode.SyncChatRunMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RunModeResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunModeResolver.class);

    private static final String CONTACTS_ADDING = "contactsAdding";
    private static final String SYNC_CHATS = "syncChats";
    private static final String DEFAULT_MODE = "default";

    private static Map<String, RunMode> modes = new HashMap<String, RunMode>(){{
        put(CONTACTS_ADDING, new ContactsAddingRunMode());
        put(SYNC_CHATS, new SyncChatRunMode());
        put(DEFAULT_MODE, new DefaultRunMode());
    }};

    public static RunMode resolveRunMode(final String[] args) {
        if (args.length > 0) {
            final String mode = args[0];
            RunMode runMode = modes.get(mode);
            if (runMode != null) {
                return runMode;
            } else {
                LOGGER.error("Wrong running mode '{}'", mode);
                System.exit(0);
            }
        } else {
            return modes.get(DEFAULT_MODE);
        }
        throw new IllegalArgumentException("Something wrong in resolving of running mode");
    }
}
