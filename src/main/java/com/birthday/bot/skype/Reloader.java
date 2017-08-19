package com.birthday.bot.skype;

import com.birthday.bot.skype.bot.listener.Subscriber;
import com.birthday.bot.skype.chat.ChatRepository;
import com.birthday.bot.skype.contact.ContactRepository;
import com.birthday.bot.skype.holder.SkypeHolder;

public class Reloader {

  public synchronized static void reload() {
    SkypeHolder.reBuildSkype();
    Subscriber.process();
    ContactRepository.reload();
    ChatRepository.reload();
  }
}
