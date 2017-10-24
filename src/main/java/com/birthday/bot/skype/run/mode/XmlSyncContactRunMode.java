package com.birthday.bot.skype.run.mode;

import com.birthday.bot.db.NitriteHolder;
import com.birthday.bot.skype.contact.Contact;
import com.birthday.bot.skype.settings.BirthdayBot;
import com.birthday.bot.skype.settings.loader.BirthdayBotSettings;
import org.dizitart.no2.objects.ObjectRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;

public class XmlSyncContactRunMode extends AbstractRunMode {

  private static final Logger LOGGER = LoggerFactory.getLogger(XmlSyncContactRunMode.class);

  @Override
  public void run(String[] args) {
    NitriteHolder.init();
    addXmlContactsToNitrite();
    System.exit(0);
  }

  private void addXmlContactsToNitrite() {
    LOGGER.info("Start migration contact from xml to nitrite");
    final ObjectRepository<Contact> repository = NitriteHolder.getInstance().getRepository(Contact.class);
    final BirthdayBot configuration = BirthdayBotSettings.getInstance().getConfiguration();
    final List<com.birthday.bot.skype.settings.Contact> xmlContacts = configuration.getContacts().getContact();
    for (com.birthday.bot.skype.settings.Contact xmlContact : xmlContacts) {
      final Contact contact = new Contact();
      contact.setSkype(xmlContact.getSkype());
      contact.setbDay(getDate(xmlContact.getBDay()));
      contact.setTopicName(xmlContact.getTopicName());
      contact.setAdmin(xmlContact.isIsAdmin());
      repository.update(contact, true);
    }
    LOGGER.info("Finish migration contact from xml to nitrite");
  }

  private Date getDate(XMLGregorianCalendar bDay) {
    return new DateTime(
            bDay.getYear(),
            bDay.getMonth(),
            bDay.getDay(),
            0,
            0
    ).toDate();
  }
}
