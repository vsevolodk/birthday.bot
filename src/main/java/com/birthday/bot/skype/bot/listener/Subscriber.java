package com.birthday.bot.skype.bot.listener;

import com.birthday.bot.skype.holder.SkypeHolder;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.exceptions.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Subscriber {

  private static final Logger LOGGER = LoggerFactory.getLogger(Subscriber.class);

  public static void process() {
    Skype skype = SkypeHolder.getSkype();
    LOGGER.info("registration of chat listener...");
    skype.getEventDispatcher().registerListener(new BirthdayChatListener());
    subscribe(skype);
  }

  private static void subscribe(final Skype skype) {
    try {
      LOGGER.info("skype client subscrubing...");
      skype.subscribe();
    } catch (final ConnectionException e) {
      LOGGER.error("Skype subscribing for listener is failed", e);
      System.exit(0);
    }
  }
}
