package com.birthday.bot.skype.bot;

import com.birthday.bot.skype.chat.ChatForBDay;
import com.birthday.bot.skype.chat.ChatRepository;
import com.birthday.bot.skype.contact.ContactWithBDay;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.List;

/**
 * Created by Vsevolod Kaimashnikov on 08.03.2016.
 */
public class PingRunner extends Thread {

    @Override
    public void run() {
        while (true) {
            final ChatRepository chatRepository = ChatRepository.getInstance();

            final List<ChatForBDay> chats = chatRepository.getChats();

            for (final ChatForBDay chat : chats) {
                ContactWithBDay contactWithBDay = chat.getContactWithBDay();
                contactWithBDay.getBirthDay();

                final DateTime nextBDay = contactWithBDay.getBirthDay().withYear(DateTime.now().getYear());
                final Days days = Days.daysBetween(new DateTime().toLocalDate(), nextBDay.toLocalDate());

                try {
                    chat.sendMessage(Message.fromHtml(getMessage(days.getDays())));
                } catch (ConnectionException e) {
                    e.printStackTrace();
                }
            }

            try {
                //one day
                Thread.sleep(24 * 60 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getMessage(int days) {
        if (days != 1) {
            return String.format("<b>%s days left</b>", days);
        } else {
            return String.format("<b>%s day left</b>", days);
        }
    }
}
