package com.birthday.bot.skype.contact;

import com.birthday.bot.db.NitriteHolder;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

/**
 * Repository for storing all skype ContactWithBDay from company
 * Created by Vsevolod on 22.02.2016.
 */
public class ContactRepository {

    private static volatile ContactRepository instance;

    public static ContactRepository getInstance() {
        ContactRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (ContactRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = ContactRepositoryFactory.create();
                }
            }
        }
        return localInstance;
    }

    public synchronized static void reload() {
        instance = ContactRepositoryFactory.create();
    }

    private final Map<String, ContactWithBDay> contacts;

    public ContactRepository(final Map<String, ContactWithBDay> users) {
        this.contacts = users;
    }

    public Map<String, ContactWithBDay> getAllContactWithBDays() {
        return contacts;
    }

    public void addUser(final Contact contact) {
        final ObjectRepository<Contact> contactObjectRepository =
                NitriteHolder.getInstance().getRepository(Contact.class);

        contactObjectRepository.insert(contact);
        NitriteHolder.getInstance().commit();

        final ContactWithBDay contactWithBDay = ContactRepositoryFactory.loadContact(contact);
        contacts.put(contactWithBDay.getUsername(), contactWithBDay);
    }

    public synchronized void addContactWithBDays(final Map<String, ContactWithBDay> contactWithBDays) {
        contacts.entrySet().addAll(contactWithBDays.entrySet());
    }

    public ContactWithBDay getContactWithBDay(final String login) {
        return contacts.get(login);
    }

    /**
     * @param pUsers which are need excluded
     * @return all users from repository instead pUsers
     */
    public List<ContactWithBDay> getUsersWithout(final List<ContactWithBDay> pUsers) {
        List<ContactWithBDay> tUsers = new ArrayList<ContactWithBDay>(contacts.values());
        tUsers.removeAll(pUsers);
        return tUsers;
    }

    public void removeContact(String contactSkype) {
        final ObjectRepository<Contact> contactObjectRepository =
                NitriteHolder.getInstance().getRepository(Contact.class);

        contactObjectRepository.remove(eq("skype", contactSkype));
        NitriteHolder.getInstance().commit();
        contacts.remove(contactSkype);
    }

    public boolean addHistoryGiftForContact(String skypeLogin, Integer year, String gift) {
        final ObjectRepository<Contact> contactObjectRepository =
                NitriteHolder.getInstance().getRepository(Contact.class);

        final Contact contact =
                contactObjectRepository.find(eq("skype", skypeLogin)).iterator().next();
        contact.addHistoryGift(year, gift);

        contactObjectRepository.update(contact);
        NitriteHolder.getInstance().commit();
        return true;
    }

    public Contact getContact(String skypeLogin) {
        final ObjectRepository<Contact> repository =
                NitriteHolder.getInstance().getRepository(Contact.class);
        final Contact contact =
                repository.find(eq("skype", skypeLogin)).iterator().next();
        return contact;
    }

    public String getHistoryGiftOfContactAsString(String skypeLogin) {
        final Contact contact = getContact(skypeLogin);
        StringBuilder stringBuilder = new StringBuilder();
        final Map<Integer, String> historyMap = contact.getHistoryMap();
        if (historyMap.isEmpty()) {
            stringBuilder.append("Gift history is empty");
        } else {
            stringBuilder.append("Gift history: \n");
            stringBuilder.append("year gift");
            for (Map.Entry<Integer, String> entry : historyMap.entrySet()) {
                final Integer year = entry.getKey();
                final String gift = entry.getValue();
                stringBuilder
                        .append(year).append(" ").append(gift)
                        .append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
