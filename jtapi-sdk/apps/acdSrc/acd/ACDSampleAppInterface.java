package acd;



import javax.telephony.JtapiPeerUnavailableException;

/**
 * @author sswamy
 *
 */
public interface ACDSampleAppInterface {
	public String[] getServices(String jtapiPeerName) throws JtapiPeerUnavailableException;
	public void init();
	public void start();
	public void setInitializationParameters(final ACDSampleInitializationParameters initParams) throws Exception;
}
