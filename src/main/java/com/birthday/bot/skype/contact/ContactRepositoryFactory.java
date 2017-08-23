package com.birthday.bot.skype.contact;

import com.birthday.bot.skype.settings.Contact;
import com.birthday.bot.skype.settings.loader.BirthdayBotSettings;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.birthday.bot.skype.holder.SkypeHolder;
import com.birthday.bot.skype.settings.Contacts;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory for creating contact repository
 * Created by Vsevolod Kaimashnikov on 27.02.2016.
 */
public class ContactRepositoryFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactRepositoryFactory.class);

    public static ContactRepository create() {
        try {
            return new ContactRepository(loadContacts(SkypeHolder.getSkype()));
        } catch (ConnectionException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, ContactWithBDay> loadContacts(final Skype skype) throws ConnectionException {
        final Contacts contacts = BirthdayBotSettings.getInstance().getConfiguration().getContacts();

        final List<Contact> contactList = contacts.getContact();

        final Map<String, ContactWithBDay> result = new HashMap<String, ContactWithBDay>(contactList.size());
        for (final Contact contact : contactList) {
            final String skypeId = contact.getSkype();
            final DateTime bDay = getDate(contact.getBDay());
            final String topicName = contact.getTopicName();
            final boolean isAdmin = contact.isIsAdmin();

            com.samczsun.skype4j.participants.info.Contact skypeContact = skype.getOrLoadContact(skypeId);

            final ContactWithBDay contactWithBDay = new ContactWithBDay(skypeContact, bDay, topicName, isAdmin);

            result.put(contactWithBDay.getUsername(), contactWithBDay);
        }

        if (result.size() != contactList.size()) {
            LOGGER.error("Loaded contacts size doesn't equal to settings contact size");
        }

        return result;
    }

    private static DateTime getDate(XMLGregorianCalendar bDay) {
        return new DateTime(bDay.getYear(), bDay.getMonth(), bDay.getDay(), 0, 0);
    }
}
