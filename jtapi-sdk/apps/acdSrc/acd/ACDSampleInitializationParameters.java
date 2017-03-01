package acd;

/**
 * 
 */

/**
 * 
 * JTAPICLASSNAME,
	SERVICENAME,
	LOGIN,
	PASSWORD,
	ACD,
	AGENT1,
	AGENT2,
	STATION1,
	STATION2;
 * @author sswamy
 *
 */
public class ACDSampleInitializationParameters {
	
	private String jtapiClassName;
	private String serviceName;
	private String userName;
	private String password;
	private String acdAddress;
	private String agentOneAddress;
	private String agentTwoAddress;
	private String agentOnePassword;
	private String agentTwoPassword;
	private String agentOneStation;
	private String agentTwoStation;
	/**
	 * @return the jtapiClassName
	 */
	public String getJtapiClassName() {
		return jtapiClassName;
	}
	/**
	 * @param jtapiClassName the jtapiClassName to set
	 */
	public void setJtapiClassName(String jtapiClassName) {
		this.jtapiClassName = jtapiClassName;
	}
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the acdAddress
	 */
	public String getAcdAddress() {
		return acdAddress;
	}
	/**
	 * @param acdAddress the acdAddress to set
	 */
	public void setAcdAddress(String acdAddress) {
		this.acdAddress = acdAddress;
	}
	/**
	 * @return the agentOneAddress
	 */
	public String getAgentOneAddress() {
		return agentOneAddress;
	}
	/**
	 * @param agentOneAddress the agentOneAddress to set
	 */
	public void setAgentOneAddress(String agentOneAddress) {
		this.agentOneAddress = agentOneAddress;
	}
	/**
	 * @return the agentTwoAddress
	 */
	public String getAgentTwoAddress() {
		return agentTwoAddress;
	}
	/**
	 * @param agentTwoAddress the agentTwoAddress to set
	 */
	public void setAgentTwoAddress(String agentTwoAddress) {
		this.agentTwoAddress = agentTwoAddress;
	}
	/**
	 * @return the agentOneStation
	 */
	public String getAgentOneStation() {
		return agentOneStation;
	}
	/**
	 * @param agentOneStation the agentOneStation to set
	 */
	public void setAgentOneStation(String agentOneStation) {
		this.agentOneStation = agentOneStation;
	}
	/**
	 * @return the agentTwoStation
	 */
	public String getAgentTwoStation() {
		return agentTwoStation;
	}
	/**
	 * @param agentTwoStation the agentTwoStation to set
	 */
	public void setAgentTwoStation(String agentTwoStation) {
		this.agentTwoStation = agentTwoStation;
	}
	public String getAgentTwoPassword() {
		return agentTwoPassword;
	}
	public String getAgentOnePassword() {
		return agentOnePassword;
	}
	public void setAgentOnePassword(String agentOnePassword) {
		this.agentOnePassword = agentOnePassword;
	}
	public void setAgentTwoPassword(String agentTwoPassword) {
		this.agentTwoPassword = agentTwoPassword;
	}
	

}
