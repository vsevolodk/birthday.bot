package com.birthday.bot.skype.chat;

import com.birthday.bot.tools.Const;
import com.birthday.bot.tools.serialization.SerializationHelper;
import com.birthday.bot.tools.serialization.XStreamSerializationHelper;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.participants.info.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.birthday.bot.tools.file.FileUtils.*;

import java.util.*;

/**
 * Repository for active birthday chats.
 * <p/>
 * Created by Vsevolod Kaimashnikov on 22.02.2016.
 */
public class ChatRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatRepository.class);

    private static volatile ChatRepository instance;

    public static ChatRepository getInstance() {
        ChatRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (ChatRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = ChatRepositoryFactory.create();
                }
            }
        }
        return localInstance;
    }

    public synchronized static void reload() {
        instance = ChatRepositoryFactory.create();
    }

    private final Map<String, ChatForBDay> chats;

    private final SerializationHelper<ChatForBDayState> serializationHelper = new XStreamSerializationHelper<ChatForBDayState>();

    public ChatRepository(final Map<String, ChatForBDay> chats) {
        this.chats = chats;
    }

    public void addOptionForChat(final String identity, String option) {
        ChatForBDay groupChat = getGroupChat(identity);
        groupChat.getState().addOption(option);
        deleteChatAsXML(chats.get(identity).getState());
        saveChatAsXML(groupChat.getState());
    }

    public List<ChatForBDay> getChats() {
        return new ArrayList<ChatForBDay>(chats.values());
    }

    public void addChat(final ChatForBDay chat) {
        if (chat == null) {
            LOGGER.info("ChatRepository.addChat: chat is null");
            return;
        }
        final ChatForBDayState state = chat.getState();
        if (state.getFileName() == null) {
            state.setFileName(UUID.randomUUID().toString());
        }
        saveChatAsXML(state);
        chats.put(chat.getIdentity(), chat);
        LOGGER.info(
                "Chat is added to repository: {} for {}",
                chat.getTopic(),
                chat.getContactWithBDay().getUsername()
        );
    }

    private void saveChatAsXML(final ChatForBDayState state) {
        final String xml = serializationHelper.toXML(state);
        write(Const.USER_DIR + "/chats/" + state.getFileName() + ".xml", xml);
    }

    private void deleteChatAsXML(final ChatForBDayState state) {
        delete(Const.USER_DIR + "/chats/" + state.getFileName() + ".xml");
    }

    public void deleteChat(final String identity) {
        final ChatForBDay chat = chats.remove(identity);
        deleteChatAsXML(chat.getState());
        try {
            chat.sendMessage(Message.fromHtml("<b>Here my work is finished<b>"));
            chat.leave();
        } catch (Exception e) {
            LOGGER.error("Leaving of chat " + identity + "is failed", e);
        }
    }

    public boolean isChatExistForUser(Contact contact) {
        for (ChatForBDay chatForBDay : chats.values()) {
            String userName = chatForBDay.getContactWithBDay().getUsername();
            if (contact.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public ChatForBDay getGroupChat(final String identity) {
        return chats.get(identity);
    }
}
