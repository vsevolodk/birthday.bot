package com.birthday.bot.skype.chat;

import com.birthday.bot.skype.contact.ContactWithBDay;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.GroupChat;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.events.chat.user.action.OptionUpdateEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.IMoji;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.user.Contact;
import com.samczsun.skype4j.user.User;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vsevolod Kaimashnikov on 28.02.2016.
 */
public class ChatForBDay implements GroupChat {

    private final GroupChat groupChat;
    private final ContactWithBDay contactWithBDay;
    private final ChatForBDayState state;

    public ChatForBDay(
            final GroupChat groupChat,
            final ContactWithBDay contactWithBDay
    ) {
        this.groupChat = groupChat;
        this.contactWithBDay = contactWithBDay;
        this.state = new ChatForBDayState(groupChat.getIdentity(), contactWithBDay.getUsername());
    }

    public ContactWithBDay getContactWithBDay() {
        return contactWithBDay;
    }

    @Override
    public int hashCode() {
        return groupChat.getIdentity().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chat) {
            Chat chat = (Chat) obj;
            return this.groupChat.getIdentity().equals(chat.getIdentity());
        } else {
            return false;
        }
    }

    public List<String> getOptions() {
        return state.getOptions();
    }

    @Override
    public void add(Contact contact) throws ConnectionException {
        groupChat.add(contact);
    }

    @Override
    public void alertsOff() throws ConnectionException {
        groupChat.alertsOff();
    }

    @Override
    public void alertsOn() throws ConnectionException {
        groupChat.alertsOn();
    }

    @Override
    public void alertsOn(String keyword) throws ConnectionException {
        groupChat.alertsOn(keyword);
    }

    @Override
    public List<ChatMessage> getAllMessages() {
        return groupChat.getAllMessages();
    }

    @Override
    public Collection<User> getAllUsers() {
        return groupChat.getAllUsers();
    }

    @Override
    public Skype getClient() {
        return groupChat.getClient();
    }

    @Override
    public String getIdentity() {
        return groupChat.getIdentity();
    }

    @Override
    public String getJoinUrl() throws ConnectionException {
        return groupChat.getJoinUrl();
    }

    @Override
    public BufferedImage getPicture() throws ConnectionException {
        return groupChat.getPicture();
    }

    @Override
    public User getSelf() {
        return groupChat.getSelf();
    }

    @Override
    public String getTopic() {
        return groupChat.getTopic();
    }

    @Override
    public User getUser(String username) {
        return groupChat.getUser(username);
    }

    @Override
    public boolean isLoaded() {
        return groupChat.isLoaded();
    }

    @Override
    public boolean isOptionEnabled(OptionUpdateEvent.Option option) {
        return groupChat.isOptionEnabled(option);
    }

    @Override
    public void kick(String username) throws ConnectionException {
        groupChat.kick(username);
    }

    @Override
    public void leave() throws ConnectionException {
        groupChat.leave();
    }

    @Override
    public List<ChatMessage> loadMoreMessages(int amount) throws ConnectionException {
        return groupChat.loadMoreMessages(amount);
    }

    @Override
    public void sendContact(Contact contact) throws ConnectionException {
        groupChat.sendContact(contact);
    }

    @Override
    public void sendFile(File file) throws ConnectionException {
        groupChat.sendFile(file);
    }

    @Override
    public void sendImage(BufferedImage image, String imageType, String imageName) throws ConnectionException, IOException {
        groupChat.sendImage(image, imageType, imageName);
    }

    @Override
    public void sendImage(File image) throws ConnectionException, IOException {
        groupChat.sendImage(image);
    }

    @Override
    public ChatMessage sendMessage(Message message) throws ConnectionException {
        return groupChat.sendMessage(message);
    }

    @Override
    public ChatMessage sendMessage(String plainMessage) throws ConnectionException {
        return groupChat.sendMessage(plainMessage);
    }

    @Override
    public void sendMoji(IMoji moji) throws ConnectionException {
        groupChat.sendMoji(moji);
    }

    @Override
    public void setImage(File file) throws ConnectionException, IOException {
        groupChat.setImage(file);
    }

    @Override
    public void setImage(BufferedImage image, String imageType) throws ConnectionException, IOException {
        groupChat.setImage(image, imageType);
    }

    @Override
    public void setOptionEnabled(OptionUpdateEvent.Option option, boolean enabled) throws ConnectionException {
        groupChat.setOptionEnabled(option, enabled);
    }

    @Override
    public void setTopic(String topic) throws ConnectionException {
        groupChat.setTopic(topic);
    }

    public ChatForBDayState getState() {
        return state;
    }
}
