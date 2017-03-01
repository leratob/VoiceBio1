
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="clientConfigName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="callId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentExtension" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localPartyIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localPartyPort" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/>
 *         &lt;element name="callDirection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="callerLineIdentity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "clientConfigName",
    "callId",
    "agentId",
    "agentExtension",
    "localPartyIP",
    "localPartyPort",
    "callDirection",
    "callerLineIdentity"
})
@XmlRootElement(name = "CallConnected")
public class CallConnected {

    @XmlElementRef(name = "clientConfigName", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> clientConfigName;
    @XmlElementRef(name = "callId", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> callId;
    @XmlElementRef(name = "agentId", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> agentId;
    @XmlElementRef(name = "agentExtension", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> agentExtension;
    @XmlElementRef(name = "localPartyIP", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> localPartyIP;
    @XmlSchemaType(name = "unsignedShort")
    protected Integer localPartyPort;
    @XmlElementRef(name = "callDirection", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> callDirection;
    @XmlElementRef(name = "callerLineIdentity", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> callerLineIdentity;

    /**
     * Gets the value of the clientConfigName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getClientConfigName() {
        return clientConfigName;
    }

    /**
     * Sets the value of the clientConfigName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setClientConfigName(JAXBElement<String> value) {
        this.clientConfigName = value;
    }

    /**
     * Gets the value of the callId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCallId() {
        return callId;
    }

    /**
     * Sets the value of the callId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCallId(JAXBElement<String> value) {
        this.callId = value;
    }

    /**
     * Gets the value of the agentId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAgentId() {
        return agentId;
    }

    /**
     * Sets the value of the agentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAgentId(JAXBElement<String> value) {
        this.agentId = value;
    }

    /**
     * Gets the value of the agentExtension property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAgentExtension() {
        return agentExtension;
    }

    /**
     * Sets the value of the agentExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAgentExtension(JAXBElement<String> value) {
        this.agentExtension = value;
    }

    /**
     * Gets the value of the localPartyIP property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLocalPartyIP() {
        return localPartyIP;
    }

    /**
     * Sets the value of the localPartyIP property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLocalPartyIP(JAXBElement<String> value) {
        this.localPartyIP = value;
    }

    /**
     * Gets the value of the localPartyPort property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLocalPartyPort() {
        return localPartyPort;
    }

    /**
     * Sets the value of the localPartyPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLocalPartyPort(Integer value) {
        this.localPartyPort = value;
    }

    /**
     * Gets the value of the callDirection property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCallDirection() {
        return callDirection;
    }

    /**
     * Sets the value of the callDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCallDirection(JAXBElement<String> value) {
        this.callDirection = value;
    }

    /**
     * Gets the value of the callerLineIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCallerLineIdentity() {
        return callerLineIdentity;
    }

    /**
     * Sets the value of the callerLineIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCallerLineIdentity(JAXBElement<String> value) {
        this.callerLineIdentity = value;
    }

}
