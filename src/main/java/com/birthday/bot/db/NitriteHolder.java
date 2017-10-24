package com.birthday.bot.db;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NitriteHolder {

  private static final Logger LOGGER = LoggerFactory.getLogger(NitriteHolder.class);

  private static final String CONTACTS_REPOSITORY = "contacts-repository";

  private static volatile Nitrite instance;

  public static void init() {
    synchronized (NitriteHolder.class) {
      instance = internalInit();
    }
  }

  public static Nitrite getInstance() {
    return instance;
  }

  private static Nitrite internalInit() {
    String dbName = "nitrite-birthday-bot-db";
    NitriteBuilder nitriteBuilder = Nitrite.builder()
            .compressed()
            .filePath("./" + dbName + ".db");
    final Nitrite db;
    if (true) {
      db = nitriteBuilder.openOrCreate();
    } else {
      //todo: support it in bot configuration
      db = nitriteBuilder.openOrCreate("user", "password");
    }

    return db;
  }
}
