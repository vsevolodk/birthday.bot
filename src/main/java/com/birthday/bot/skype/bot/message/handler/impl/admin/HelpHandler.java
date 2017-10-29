package com.birthday.bot.skype.bot.message.handler.impl.admin;

import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelpHandler extends AbstractAdminHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(HelpHandler.class);

  private static final Message helpMessage = Message.fromHtml(
          "0. \\bHelp - command for list of available command display \n"
            + "1. \\bStatus - command for common metrics display \n"
            + "2. \\bAddContact skype;yyyy-mm-dd;topic;[true|false] - command for adding new contact \n"
            + "3. \\bRmContact skype - command for removing exist contact \n"
            + "4. \\bPing - command for ping all exist chats \n"
            + "5. \\bCreate - command for manual create comming soon chats \n"
            + "6. \\bShowContacts - command for display current contact list \n"
            + "7. \\bShowChats - command for display current chat list \n"
            + "8. \\bAddHistoryGift skype;year;gift - command for add gift for year \n"
  );

  @Override
  protected Message getMessage(MessageReceivedEvent messageReceivedEvent) {
    return helpMessage;
  }
}