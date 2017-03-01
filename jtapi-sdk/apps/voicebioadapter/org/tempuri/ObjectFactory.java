
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
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

    private final static QName _CallHoldCallId_QNAME = new QName("http://tempuri.org/", "callId");
    private final static QName _CallConnectedCallDirection_QNAME = new QName("http://tempuri.org/", "callDirection");
    private final static QName _CallConnectedCallerLineIdentity_QNAME = new QName("http://tempuri.org/", "callerLineIdentity");
    private final static QName _CallConnectedLocalPartyIP_QNAME = new QName("http://tempuri.org/", "localPartyIP");
    private final static QName _CallConnectedClientConfigName_QNAME = new QName("http://tempuri.org/", "clientConfigName");
    private final static QName _CallConnectedAgentId_QNAME = new QName("http://tempuri.org/", "agentId");
    private final static QName _CallConnectedAgentExtension_QNAME = new QName("http://tempuri.org/", "agentExtension");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CallUnHold }
     * 
     */
    public CallUnHold createCallUnHold() {
        return new CallUnHold();
    }

    /**
     * Create an instance of {@link CallConnected }
     * 
     */
    public CallConnected createCallConnected() {
        return new CallConnected();
    }

    /**
     * Create an instance of {@link CallHold }
     * 
     */
    public CallHold createCallHold() {
        return new CallHold();
    }

    /**
     * Create an instance of {@link CallDisconnected }
     * 
     */
    public CallDisconnected createCallDisconnected() {
        return new CallDisconnected();
    }

    /**
     * Create an instance of {@link CallHoldResponse }
     * 
     */
    public CallHoldResponse createCallHoldResponse() {
        return new CallHoldResponse();
    }

    /**
     * Create an instance of {@link CallUnHoldResponse }
     * 
     */
    public CallUnHoldResponse createCallUnHoldResponse() {
        return new CallUnHoldResponse();
    }

    /**
     * Create an instance of {@link CallConnectedResponse }
     * 
     */
    public CallConnectedResponse createCallConnectedResponse() {
        return new CallConnectedResponse();
    }

    /**
     * Create an instance of {@link CallDisconnectedResponse }
     * 
     */
    public CallDisconnectedResponse createCallDisconnectedResponse() {
        return new CallDisconnectedResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "callId", scope = CallHold.class)
    public JAXBElement<String> createCallHoldCallId(String value) {
        return new JAXBElement<String>(_CallHoldCallId_QNAME, String.class, CallHold.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "callId", scope = CallDisconnected.class)
    public JAXBElement<String> createCallDisconnectedCallId(String value) {
        return new JAXBElement<String>(_CallHoldCallId_QNAME, String.class, CallDisconnected.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "callId", scope = CallUnHold.class)
    public JAXBElement<String> createCallUnHoldCallId(String value) {
        return new JAXBElement<String>(_CallHoldCallId_QNAME, String.class, CallUnHold.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "callDirection", scope = CallConnected.class)
    public JAXBElement<String> createCallConnectedCallDirection(String value) {
        return new JAXBElement<String>(_CallConnectedCallDirection_QNAME, String.class, CallConnected.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "callerLineIdentity", scope = CallConnected.class)
    public JAXBElement<String> createCallConnectedCallerLineIdentity(String value) {
        return new JAXBElement<String>(_CallConnectedCallerLineIdentity_QNAME, String.class, CallConnected.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "localPartyIP", scope = CallConnected.class)
    public JAXBElement<String> createCallConnectedLocalPartyIP(String value) {
        return new JAXBElement<String>(_CallConnectedLocalPartyIP_QNAME, String.class, CallConnected.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "callId", scope = CallConnected.class)
    public JAXBElement<String> createCallConnectedCallId(String value) {
        return new JAXBElement<String>(_CallHoldCallId_QNAME, String.class, CallConnected.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "clientConfigName", scope = CallConnected.class)
    public JAXBElement<String> createCallConnectedClientConfigName(String value) {
        return new JAXBElement<String>(_CallConnectedClientConfigName_QNAME, String.class, CallConnected.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "agentId", scope = CallConnected.class)
    public JAXBElement<String> createCallConnectedAgentId(String value) {
        return new JAXBElement<String>(_CallConnectedAgentId_QNAME, String.class, CallConnected.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "agentExtension", scope = CallConnected.class)
    public JAXBElement<String> createCallConnectedAgentExtension(String value) {
        return new JAXBElement<String>(_CallConnectedAgentExtension_QNAME, String.class, CallConnected.class, value);
    }

}
