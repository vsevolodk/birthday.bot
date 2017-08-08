package com.birthday.bot.skype;

import com.birthday.bot.skype.chat.ChatRepository;
import com.birthday.bot.skype.contact.ContactRepository;
import com.birthday.bot.skype.holder.SkypeHolder;

public class Reloader {

  public synchronized static void reload() {
    SkypeHolder.reBuildSkype();
    ChatRepository.reload();
    ContactRepository.reload();
  }
}
