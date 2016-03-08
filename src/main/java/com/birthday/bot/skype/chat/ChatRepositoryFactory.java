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

        final Map<String, ChatForBDay> result = new HashMap<String, ChatForBDay>();
        while (iterator.hasNext()) {
            final File childFile = (File) iterator.next();
            final String xml = readFile(childFile.getAbsolutePath());
            final ChatForBDayState chatForBDayState = SERIALIZATION_HELPER.fromXML(xml);

            final Chat loadChat = skype.getOrLoadChat(chatForBDayState.getIdentity());
            final ChatForBDay chatForBDay = new ChatForBDay(
                    (GroupChat)loadChat,
                    ContactRepository.getInstance().getContactWithBDay(chatForBDayState.getbDayHuman())
            );

            result.put(loadChat.getIdentity(), chatForBDay);
        }
        return result;
    }
}