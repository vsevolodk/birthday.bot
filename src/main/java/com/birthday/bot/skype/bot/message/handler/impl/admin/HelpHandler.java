package com.birthday.bot.skype.bot.message.handler.impl.admin;

import com.samczsun.skype4j.formatting.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelpHandler extends AbstractAdminCommand {

  private static final Logger LOGGER = LoggerFactory.getLogger(HelpHandler.class);

  private static final Message helpMessage = Message.fromHtml(
          "0. \\bHelp - command for list of available command display \n"
            + "1. \\bStatus - command for common metrics display \n"
            + "2. \\bAddContact - command for adding new contact (WIP) \n"
            + "3. \\bRemoveContact - command for removing new contact (WIP)"
  );

  @Override
  protected Message getMessage() {
    return helpMessage;
  }
}