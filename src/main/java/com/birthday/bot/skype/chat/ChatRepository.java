package com.birthday.bot.skype.chat;

import com.birthday.bot.db.NitriteHolder;
import com.birthday.bot.skype.contact.ContactRepository;
import com.birthday.bot.skype.contact.ContactWithBDay;
import com.birthday.bot.tools.Const;
import com.birthday.bot.tools.serialization.SerializationHelper;
import com.birthday.bot.tools.serialization.XStreamSerializationHelper;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.participants.info.Contact;
import org.dizitart.no2.objects.ObjectRepository;
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

    public ChatRepository(final Map<String, ChatForBDay> chats) {
        this.chats = chats;
    }

    public void addOptionForChat(final String identity, String option) {
      ChatForBDay groupChat = getGroupChat(identity);
      final ChatForBDayState state = groupChat.getState();
      state.addOption(option);
      final ObjectRepository<ChatForBDayState> repository =
              NitriteHolder.getInstance().getRepository(ChatForBDayState.class);
      repository.update(state);
      NitriteHolder.getInstance().commit();
    }

    public List<ChatForBDay> getChats() {
        return new ArrayList<>(chats.values());
    }

    public void addChat(final ChatForBDay chat) {
      if (chat == null) {
        LOGGER.info("ChatRepository.addChat: chat is null");
        return;
      }
      final ChatForBDayState state = chat.getState();
      final ObjectRepository<ChatForBDayState> repository =
              NitriteHolder.getInstance().getRepository(ChatForBDayState.class);
      repository.insert(state);
      NitriteHolder.getInstance().commit();
      chats.put(chat.getIdentity(), chat);
      LOGGER.info(
              "Chat is added to repository: {} for {}",
              chat.getTopic(),
              chat.getContactWithBDay().getUsername()
      );
    }

    public void deleteChat(final String identity) {
      final ChatForBDay chat = chats.remove(identity);
      final ObjectRepository<ChatForBDayState> repository =
              NitriteHolder.getInstance().getRepository(ChatForBDayState.class);
      repository.remove(chat.getState());
      NitriteHolder.getInstance().commit();
      try {
        chat.sendMessage(Message.fromHtml("<b>Here my work is finished<b>"));
        chat.leave(); // todo: leaving does not work
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

  public boolean addNewContactForAllChats(String contactSkype) {
    try {

      final ContactWithBDay contactWithBDay =
              ContactRepository.getInstance().getContactWithBDay(contactSkype);
      if (contactWithBDay != null) {
        for (Map.Entry<String, ChatForBDay> entry : chats.entrySet()) {
          final String birthdayConactSkype = entry.getKey();
          final ChatForBDay chatForBDay = entry.getValue();
          if (!birthdayConactSkype.equals(contactSkype)) {
            chatForBDay.add(contactWithBDay);
          }
        }

        LOGGER.info("Adding new contact {} for all current chats", contactSkype);
        return true;
      }
    } catch (ConnectionException e) {
      LOGGER.error("Error during adding new contact " + contactSkype, e);
    }
    return false;
  }

  public boolean removeContactFromAllChatsAndHisChat(String contactSkype) {
    try {

      final ContactWithBDay contactWithBDay =
              ContactRepository.getInstance().getContactWithBDay(contactSkype);
      if (contactWithBDay != null) {
        for (Map.Entry<String, ChatForBDay> entry : chats.entrySet()) {
          final String birthdayConactSkype = entry.getKey();
          final ChatForBDay chatForBDay = entry.getValue();
          if (!birthdayConactSkype.equals(contactSkype)) {
            chatForBDay.kick(contactSkype);
          } else {
            deleteChat(chatForBDay.getIdentity());
          }
        }

        LOGGER.info(
                "Contact {} is removed for all current chats and his chat",
                contactSkype
        );
        return true;
      }
    } catch (ConnectionException e) {
      LOGGER.error("Error during removing contact " + contactSkype, e);
    }
    return false;
  }
}
