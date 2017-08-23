package com.birthday.bot.skype.contact;

import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.exceptions.ChatNotFoundException;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.NoSuchContactException;
import com.samczsun.skype4j.participants.info.Contact;
import org.joda.time.DateTime;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * Skype contact with birthday
 * Created by Vsevolod Kaimashnikov on 22.02.2016.
 */
public class ContactWithBDay implements Contact {

    private final Contact contact;
    private final DateTime birthDay;
    private final String topicName;
    private final boolean isAdmin;

    public ContactWithBDay(Contact contact, DateTime date, String topicName, boolean isAdmin) {
        this.contact = contact;
        this.birthDay = date;
        this.topicName = topicName;
        this.isAdmin=isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public void authorize() throws ConnectionException {
        contact.authorize();
    }

    @Override
    public void block(boolean reportAbuse) throws ConnectionException {
        contact.block(reportAbuse);
    }

    @Override
    public BufferedImage getAvatarPicture() throws ConnectionException {
        return contact.getAvatarPicture();
    }

    @Override
    public String getAvatarURL() {
        return contact.getAvatarURL();
    }

    @Override
    public String getCity() {
        return contact.getCity();
    }

    @Override
    public String getCountry() {
        return contact.getCountry();
    }

    @Override
    public String getDisplayName() {
        return contact.getDisplayName();
    }

    @Override
    public String getFirstName() {
        return contact.getFirstName();
    }

    @Override
    public String getLastName() {
        return contact.getLastName();
    }

    @Override
    public String getMood() {
        return contact.getMood();
    }

    @Override
    public Chat getPrivateConversation() throws ConnectionException, ChatNotFoundException {
        return contact.getPrivateConversation();
    }

    @Override
    public String getRichMood() {
        return contact.getRichMood();
    }

    @Override
    public String getUsername() {
        return contact.getUsername();
    }

    @Override
    public boolean isAuthorized() {
        return contact.isAuthorized();
    }

    @Override
    public boolean isBlocked() {
        return contact.isBlocked();
    }

    @Override
    public boolean isPhone() {
        return contact.isPhone();
    }

    @Override
    public void sendRequest(String message) throws ConnectionException, NoSuchContactException {
        contact.sendRequest(message);
    }

    @Override
    public void unauthorize() throws ConnectionException {
        contact.unauthorize();
    }

    @Override
    public void unblock() throws ConnectionException {
        contact.unblock();
    }

    public DateTime getBirthDay() {
        return birthDay;
    }

    @Override
    public int hashCode() {
        return contact.getUsername().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Contact) {
            Contact contact = (Contact) obj;
            return this.contact.getUsername().equals(contact.getUsername());
        } else {
            return false;
        }
    }

    public String getTopicName() {
        return topicName;
    }
}
