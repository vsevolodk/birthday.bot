package com.birthday.bot.skype.chat;

import com.birthday.bot.db.NitriteHolder;
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
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.birthday.bot.tools.Const.USER_DIR;
import static com.birthday.bot.tools.file.FileUtils.readFile;

/**
 * Created by Vsevolod Kaimashnikov on 28.02.2016.
 */
public class ChatRepositoryFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatRepositoryFactory.class);

    private static final String MANY_REQUEST_CODE = "Response: 429";

    public static ChatRepository create() {
        try {
            return new ChatRepository(loadChats(SkypeHolder.getSkype()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, ChatForBDay> loadChats(final Skype skype) throws ConnectionException, ChatNotFoundException, IOException {
        final Nitrite nitrite = NitriteHolder.getInstance();
        final ObjectRepository<ChatForBDayState> chatRepository = nitrite.getRepository(ChatForBDayState.class);

        int documentsCount = 0;

        final Map<String, ChatForBDay> result = new HashMap<>();
        for (ChatForBDayState chatState : chatRepository.find()) {

            documentsCount++;

            String chatIdentity = chatState.getIdentity();
            String human = chatState.getbDayHuman();

            final Chat loadChat = getWithPauseIfNeededChat(skype, chatIdentity);
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
        if (documentsCount != result.size()) {
            LOGGER.error("Loaded chats size doesn't equal to stored chat size");
        }
        return result;
    }

    private static Chat getWithPauseIfNeededChat(Skype skype, String chatIdentity) {
        Chat result = null;
        boolean wasError = false;
        while (result == null) {
            try {
                result = skype.getOrLoadChat(chatIdentity);
            } catch (Exception e) {
                wasError = true;
                if (e.getMessage().contains(MANY_REQUEST_CODE)) {
                    LOGGER.info("Wait 3 minutes, because many request error from skype");
                    try {
                        TimeUnit.MINUTES.sleep(3);
                    } catch (InterruptedException ee) {
                        LOGGER.error("Waiting is interrupted", ee);
                    }
                } else {
                    LOGGER.error("Some error in getWithPauseIfNeededChat method", e);
                }
            }
            LOGGER.error("Iteration of loading chat is finished");
        }
        if (wasError) {
            LOGGER.info("After some iterations all is ok");
        }
        return result;
    }
}