
package com.birthday.bot.skype.settings;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.birthday.bot.skype.settings package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BDay_QNAME = new QName("urn:birthdaybot:1.0", "bDay");
    private final static QName _Skype_QNAME = new QName("urn:birthdaybot:1.0", "skype");
    private final static QName _SchedulingTime_QNAME = new QName("urn:birthdaybot:1.0", "schedulingTime");
    private final static QName _SendRequestMessage_QNAME = new QName("urn:birthdaybot:1.0", "sendRequestMessage");
    private final static QName _SkypeLiveInMinutesBeforeReload_QNAME = new QName("urn:birthdaybot:1.0", "skypeLiveInMinutesBeforeReload");
    private final static QName _Login_QNAME = new QName("urn:birthdaybot:1.0", "login");
    private final static QName _Interval_QNAME = new QName("urn:birthdaybot:1.0", "interval");
    private final static QName _IsAdmin_QNAME = new QName("urn:birthdaybot:1.0", "isAdmin");
    private final static QName _TopicName_QNAME = new QName("urn:birthdaybot:1.0", "topicName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.birthday.bot.skype.settings
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BirthdayBot }
     * 
     */
    public BirthdayBot createBirthdayBot() {
        return new BirthdayBot();
    }

    /**
     * Create an instance of {@link Contacts }
     * 
     */
    public Contacts createContacts() {
        return new Contacts();
    }

    /**
     * Create an instance of {@link Contact }
     * 
     */
    public Contact createContact() {
        return new Contact();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:birthdaybot:1.0", name = "bDay")
    public JAXBElement<XMLGregorianCalendar> createBDay(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_BDay_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:birthdaybot:1.0", name = "skype")
    public JAXBElement<String> createSkype(String value) {
        return new JAXBElement<String>(_Skype_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:birthdaybot:1.0", name = "schedulingTime")
    public JAXBElement<String> createSchedulingTime(String value) {
        return new JAXBElement<String>(_SchedulingTime_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:birthdaybot:1.0", name = "sendRequestMessage")
    public JAXBElement<String> createSendRequestMessage(String value) {
        return new JAXBElement<String>(_SendRequestMessage_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:birthdaybot:1.0", name = "skypeLiveInMinutesBeforeReload")
    public JAXBElement<BigInteger> createSkypeLiveInMinutesBeforeReload(BigInteger value) {
        return new JAXBElement<BigInteger>(_SkypeLiveInMinutesBeforeReload_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:birthdaybot:1.0", name = "login")
    public JAXBElement<String> createLogin(String value) {
        return new JAXBElement<String>(_Login_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:birthdaybot:1.0", name = "interval")
    public JAXBElement<BigInteger> createInterval(BigInteger value) {
        return new JAXBElement<BigInteger>(_Interval_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:birthdaybot:1.0", name = "isAdmin")
    public JAXBElement<Boolean> createIsAdmin(Boolean value) {
        return new JAXBElement<Boolean>(_IsAdmin_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:birthdaybot:1.0", name = "topicName")
    public JAXBElement<String> createTopicName(String value) {
        return new JAXBElement<String>(_TopicName_QNAME, String.class, null, value);
    }

}
