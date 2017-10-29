package com.birthday.bot.skype.contact;

import com.birthday.bot.db.NitriteHolder;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.birthday.bot.skype.holder.SkypeHolder;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating contact repository
 * Created by Vsevolod Kaimashnikov on 27.02.2016.
 */
public class ContactRepositoryFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactRepositoryFactory.class);

    public static ContactRepository create() {
        try {
            return new ContactRepository(loadContacts());
        } catch (ConnectionException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, ContactWithBDay> loadContacts() throws ConnectionException {
        final Nitrite nitrite = NitriteHolder.getInstance();
        final ObjectRepository<Contact> contactRepository = nitrite.getRepository(Contact.class);

        final Map<String, ContactWithBDay> result = new HashMap<>();
        for (Contact contact : contactRepository.find()) {

            final ContactWithBDay contactWithBDay = loadContact(contact);

            if (contactWithBDay != null) {
                result.put(contactWithBDay.getUsername(), contactWithBDay);
            }
        }

        LOGGER.info("Next contacts are loaded {}", result);

        return result;
    }

    public static ContactWithBDay loadContact(Contact contact) {
        try {
            final String skypeId = contact.getSkype();
            final DateTime bDay = getDate(contact.getbDay());
            final String topicName = contact.getTopicName();
            final boolean isAdmin = contact.isAdmin();

            final Skype skype = SkypeHolder.getSkype();

            com.samczsun.skype4j.participants.info.Contact skypeContact =
                    skype.getOrLoadContact(skypeId);

            final ContactWithBDay contactWithBDay =
                    new ContactWithBDay(skypeContact, bDay, topicName, isAdmin);
            return contactWithBDay;
        } catch (ConnectionException e) {
            LOGGER.error("Error during loading of contact " + contact.getSkype(), e);
            return null;
        }
    }

    private static DateTime getDate(Date bDay) {
        return new DateTime(bDay);
    }
}
