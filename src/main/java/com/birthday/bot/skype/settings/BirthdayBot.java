
package com.birthday.bot.skype.settings;

import java.math.BigInteger;
import javax.annotation.Generated;
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
 *         &lt;element ref="{urn:birthdaybot:1.0}updateTokenMinutes"/>
 *         &lt;element ref="{urn:birthdaybot:1.0}schedulingTime"/>
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
    "updateTokenMinutes",
    "schedulingTime",
    "contacts"
})
@XmlRootElement(name = "birthdayBot")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class BirthdayBot {

    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String login;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected BigInteger interval;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String sendRequestMessage;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected BigInteger updateTokenMinutes;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String schedulingTime;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Contacts contacts;

    /**
     * Gets the value of the login property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSendRequestMessage(String value) {
        this.sendRequestMessage = value;
    }

    /**
     * Gets the value of the updateTokenMinutes property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public BigInteger getUpdateTokenMinutes() {
        return updateTokenMinutes;
    }

    /**
     * Sets the value of the updateTokenMinutes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUpdateTokenMinutes(BigInteger value) {
        this.updateTokenMinutes = value;
    }

    /**
     * Gets the value of the schedulingTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getSchedulingTime() {
        return schedulingTime;
    }

    /**
     * Sets the value of the schedulingTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSchedulingTime(String value) {
        this.schedulingTime = value;
    }

    /**
     * Gets the value of the contacts property.
     * 
     * @return
     *     possible object is
     *     {@link Contacts }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-06T01:53:04+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setContacts(Contacts value) {
        this.contacts = value;
    }

}
