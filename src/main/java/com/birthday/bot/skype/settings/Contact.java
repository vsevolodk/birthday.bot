
package com.birthday.bot.skype.settings;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element ref="{urn:birthdaybot:1.0}skype"/>
 *         &lt;element ref="{urn:birthdaybot:1.0}bDay"/>
 *         &lt;element ref="{urn:birthdaybot:1.0}topicName"/>
 *         &lt;element ref="{urn:birthdaybot:1.0}isAdmin"/>
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
    "skype",
    "bDay",
    "topicName",
    "isAdmin"
})
@XmlRootElement(name = "contact")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class Contact {

    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String skype;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected XMLGregorianCalendar bDay;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String topicName;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean isAdmin;

    /**
     * Gets the value of the skype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getSkype() {
        return skype;
    }

    /**
     * Sets the value of the skype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSkype(String value) {
        this.skype = value;
    }

    /**
     * Gets the value of the bDay property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public XMLGregorianCalendar getBDay() {
        return bDay;
    }

    /**
     * Sets the value of the bDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setBDay(XMLGregorianCalendar value) {
        this.bDay = value;
    }

    /**
     * Gets the value of the topicName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTopicName() {
        return topicName;
    }

    /**
     * Sets the value of the topicName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTopicName(String value) {
        this.topicName = value;
    }

    /**
     * Gets the value of the isAdmin property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets the value of the isAdmin property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-08-21T09:39:07+04:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsAdmin(boolean value) {
        this.isAdmin = value;
    }

}
