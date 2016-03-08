
package com.birthday.bot.skype.settings;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:birthdaybot:1.0}login"/>
 *         &lt;element ref="{urn:birthdaybot:1.0}interval"/>
 *         &lt;element ref="{urn:birthdaybot:1.0}sendRequestMessage"/>
 *         &lt;element ref="{urn:birthdaybot:1.0}contacts"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "login",
    "interval",
    "sendRequestMessage",
    "contacts"
})
@XmlRootElement(name = "birthdayBot", namespace = "urn:birthdaybot:1.0")
public class BirthdayBot {

    @XmlElement(namespace = "urn:birthdaybot:1.0", required = true)
    protected String login;
    @XmlElement(namespace = "urn:birthdaybot:1.0", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger interval;
    @XmlElement(namespace = "urn:birthdaybot:1.0", required = true)
    protected String sendRequestMessage;
    @XmlElement(namespace = "urn:birthdaybot:1.0", required = true)
    protected Contacts contacts;

    /**
     * Gets the value of the login property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the value of the login property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogin(String value) {
        this.login = value;
    }

    /**
     * Gets the value of the interval property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getInterval() {
        return interval;
    }

    /**
     * Sets the value of the interval property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setInterval(BigInteger value) {
        this.interval = value;
    }

    /**
     * Gets the value of the sendRequestMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendRequestMessage() {
        return sendRequestMessage;
    }

    /**
     * Sets the value of the sendRequestMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendRequestMessage(String value) {
        this.sendRequestMessage = value;
    }

    /**
     * Gets the value of the contacts property.
     * 
     * @return
     *     possible object is
     *     {@link Contacts }
     *     
     */
    public Contacts getContacts() {
        return contacts;
    }

    /**
     * Sets the value of the contacts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contacts }
     *     
     */
    public void setContacts(Contacts value) {
        this.contacts = value;
    }

}
