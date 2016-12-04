package com.birthday.bot.skype.run.mode;

import com.birthday.bot.skype.contact.ContactRepository;
import com.birthday.bot.skype.contact.ContactWithBDay;
import com.birthday.bot.skype.settings.loader.BirthdayBotSettings;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.NoSuchContactException;

import java.util.Collection;

public class ContactsAddingRunMode extends AbstractRunMode {

    @Override
    public void run() {
        initSkype();

        ContactRepository contactRepository = ContactRepository.getInstance();
        Collection<ContactWithBDay> contacts = contactRepository.getAllContactWithBDays().values();

        for (ContactWithBDay contact : contacts) {
            try {
                if (!contact.isAuthorized()) {
                    try {
                        contact.sendRequest(BirthdayBotSettings.getInstance().getConfiguration().getSendRequestMessage());
                        System.out.println(String.format("For %s request for adding is send", contact.getUsername()));
                    } catch (ConnectionException e) {
                        System.out.println(
                                String.format(
                                        "For user %s - %s",
                                        contact.getUsername(),
                                        e.getMessage()
                                )
                        );
                    }
                }
            } catch (NoSuchContactException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Contact adding finished");
    }
}
