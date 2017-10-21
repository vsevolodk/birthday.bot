package com.birthday.bot.skype.bot.message.handler.impl.group;

import com.birthday.bot.skype.bot.message.handler.impl.admin.AbstractAdminCommand;
import com.samczsun.skype4j.formatting.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelpHandler extends AbstractAdminCommand {

  private static final Logger LOGGER = LoggerFactory.getLogger(HelpHandler.class);

  private static final Message helpMessage = Message.fromHtml(
          "0. \\bOption - command for adding gift option for current chat \n"
            + "1. \\bShow - command for display current gift options for current chat \n"
            + "2. \\bDone - command for close activity for current chat \n"
  );

  @Override
  protected Message getMessage() {
    return helpMessage;
  }
}