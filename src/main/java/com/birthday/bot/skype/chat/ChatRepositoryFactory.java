package com.birthday.bot.skype.chat;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.GroupChat;
import com.samczsun.skype4j.exceptions.ChatNotFoundException;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.birthday.bot.skype.contact.ContactRepository;
import com.birthday.bot.skype.holder.SkypeHolder;
import com.birthday.bot.tools.serialization.SerializationHelper;
import com.birthday.bot.tools.serialization.XStreamSerializationHelper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.birthday.bot.tools.Const.USER_DIR;
import static com.birthday.bot.tools.file.FileUtils.readFile;

/**
 * Created by Vsevolod Kaimashnikov on 28.02.2016.
 */
public class ChatRepositoryFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatRepositoryFactory.class);

    public static ChatRepository create() {
        try {
            return new ChatRepository(loadChats(SkypeHolder.getSkype()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final SerializationHelper<ChatForBDayState> SERIALIZATION_HELPER = new XStreamSerializationHelper<ChatForBDayState>();

    private static Map<String, ChatForBDay> loadChats(final Skype skype) throws ConnectionException, ChatNotFoundException, IOException {
        File file = new File(USER_DIR + "/chats");
        if (!file.exists()) {
            file.mkdir();
        }
        Iterator iterator = FileUtils.iterateFiles(file, new String[]{"xml"}, false);

        int fileCount = 0;

        final Map<String, ChatForBDay> result = new HashMap<String, ChatForBDay>();
        while (iterator.hasNext()) {
            fileCount++;
            final File childFile = (File) iterator.next();
            final String xml = readFile(childFile.getAbsolutePath());
            final ChatForBDayState chatForBDayState = SERIALIZATION_HELPER.fromXML(xml);

            String chatIdentity = chatForBDayState.getIdentity();
            String human = chatForBDayState.getbDayHuman();

            final Chat loadChat = skype.getOrLoadChat(chatIdentity);
            if (loadChat != null) {
                final ChatForBDay chatForBDay = new ChatForBDay(
                        (GroupChat) loadChat,
                        ContactRepository.getInstance().getContactWithBDay(human)
                );
                result.put(loadChat.getIdentity(), chatForBDay);
            } else {
                LOGGER.error("Chat {} for {} can not be loaded", chatIdentity, human);
            }
        }
        if (fileCount != result.size()) {
            LOGGER.error("Loaded chats size doesn't equal to stored chat size");
        }
        return result;
    }
}