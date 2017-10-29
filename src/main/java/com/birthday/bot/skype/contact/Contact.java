package com.birthday.bot.skype.contact;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;
import java.util.*;

@Indices({
        @Index(value = "skype", type = IndexType.Unique)
})
public class Contact implements Serializable {

  @Id
  private String skype;

  private Date bDay;

  private String topicName;

  private boolean isAdmin;

  private Map<Integer, String> historyMap = new HashMap<>();

  public String addHistoryGift(Integer year, String gift) {
    return historyMap.put(year, gift);
  }

  public String getSkype() {
    return skype;
  }

  public void setSkype(String skype) {
    this.skype = skype;
  }

  public Map<Integer, String> getHistoryMap() {
    return Collections.unmodifiableMap(historyMap);
  }

  public Date getbDay() {
    return bDay;
  }

  public void setbDay(Date bDay) {
    this.bDay = bDay;
  }

  public String getTopicName() {
    return topicName;
  }

  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Contact contact = (Contact) o;

    return skype.equals(contact.skype);
  }

  @Override
  public int hashCode() {
    return skype.hashCode();
  }

  @Override
  public String toString() {
    return "Contact{" +
            "skype='" + skype + '\'' +
            ", bDay=" + formatDate(bDay) +
            ", topicName='" + topicName + '\'' +
            ", isAdmin=" + isAdmin +
            '}';
  }

  private String formatDate(Date date) {
    final Calendar instance = Calendar.getInstance();
    instance.setTime(date);
    final int day = instance.get(Calendar.DAY_OF_MONTH);
    final int month = instance.get(Calendar.MONTH);
    final int year = instance.get(Calendar.YEAR);
    return day + "." + month + "." + year;
  }
}
