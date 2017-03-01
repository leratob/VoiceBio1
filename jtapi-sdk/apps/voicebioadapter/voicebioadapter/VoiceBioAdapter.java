package voicebioadapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.CallEvent;
import javax.telephony.CallListener;
import javax.telephony.Connection;
import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.JtapiPeerUnavailableException;
import javax.telephony.PlatformException;
import javax.telephony.Provider;
import javax.telephony.ProviderEvent;
import javax.telephony.ProviderListener;
import javax.telephony.Terminal;
import javax.telephony.TerminalConnection;
import javax.telephony.callcontrol.CallControlTerminalConnection;
import javax.telephony.callcontrol.CallControlTerminalConnectionEvent;
import javax.telephony.callcenter.Agent;

import autoanswer.ui.AutoAnswerFrame;
import autoanswer.ui.MyTextArea;

import com.avaya.jtapi.tsapi.ITsapiCallIDPrivate;
import com.avaya.jtapi.tsapi.ITsapiConnIDPrivate;
import com.avaya.jtapi.tsapi.LucentTerminal;
import com.avaya.jtapi.tsapi.adapters.CallControlTerminalConnectionListenerAdapter;

public class VoiceBioAdapter extends CallControlTerminalConnectionListenerAdapter implements ProviderListener {
	private final MyTextArea trace;
	private JtapiPeer jtapiPeer = null;
	private Provider provider;
	private Terminal myTerminal;
	private Address myAddress;
	private Agent agent;
	private final AutoAnswerFrame parent;
	
	String clientConfigName = "SBSA";
	String strAgentId;
	String callId;
	String agentExtension;
	String localPartyIP;
	Integer localPartyPort;
	String callDirection;
	String callerLineIdentity;
	int threadNum = 0;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public VoiceBioAdapter(MyTextArea area, AutoAnswerFrame parent) {
		this.trace = area;
		this.parent = parent;
	}

	/**
	 * This method creates the default JtapiPeer.
	 */
	public boolean initJtapi() {
		
		try {
			// get default implementation of the JtapiPeer object by sending
			// null,
			// optionally you may send com.avaya.jtapi.tsapi.TsapiPeer
			jtapiPeer = JtapiPeerFactory.getJtapiPeer(null);
			trace.append(false, "JtapiPeer created successfully.\n\n");
			
		} catch (JtapiPeerUnavailableException e) {
			try {
				
				jtapiPeer = JtapiPeerFactory.getJtapiPeer("com.avaya.jtapi.tsapi.TsapiPeer");
				trace.append(false, "JtapiPeer created successfully.\n\n");
				
			} catch (JtapiPeerUnavailableException e2) {
				
				trace.append("******JtapiPeerFactory.getJtapiPeer: caught JtapiPeerUnavailableException******\n");
				trace.append("******Message: " + e2.getMessage() + "******\n\n");
				trace.append("******Error: JtapiPeer could not be created.  Verify your Jtapi client install.******\n\n");
				return false;
				
			}
		}
		return true;
	}

	/**
	 * This method creates the Provider and waits until is in service.
	 */
	public synchronized void login(Hashtable<String, String> args) {
		String serviceName;
		String login;
		String password;
		String callingExt;
		String providerString;

		serviceName = (String) args.get("serviceName");
		login = "voicebio";
		password = "Voicebio1#";
		callingExt = "7072353";
		
		//callingExt = (String) args.get("callingExt");
		//Agent ID 1032

		providerString = serviceName + ";loginID=" + login + ";passwd=" + password;

		if (jtapiPeer == null) {
			return;
		}
		

		try {
			// create provider
			provider = jtapiPeer.getProvider(providerString);
			trace.append(false, "::::::::: Provider created successfully.\n");
			trace.append(false, "::::::::: Waiting for the provider to initialize...\n");

			// add a ProviderListener to the provider
			provider.addProviderListener(this);
			
			// wait to be notified when the provider is in service --
			// corresponding notify is in the providerChangedEvent() method
			wait();
			
		} catch (Exception e) {
			
			trace.append("******login() caught " + e + "******\n");
			trace.append("******Message: " + e.getMessage() + "******\n\n");
			trace.append("******Please verify that the login information is correct.******\n\n");
			return;
		}

		trace.append(false, "::::::: Provider is in service.\n\n");
		try {
			try {
				
				// In order to make a call, we need to obtain an Address and a
				// Terminal
				// object that represent the dialing extension. In Avaya's
				// implementation
				// of JTAPI, there is a one-to-one relationship between the
				// Terminal and
				// Address objects that represent an extension number.

				// create Address
				myAddress = provider.getAddress(callingExt);
				trace.append(false, "::::::: Address " + callingExt + " created successfully.\n");
				
				// create Terminal
				myTerminal = provider.getTerminal(callingExt);
				trace.append(false, "::::::: Terminal " + callingExt + " created successfully.\n");
				
				
			} catch (Exception e) {
				
				trace.append("******Please verify that the dialing extension number is correct.******\n\n");
				throw (e);
				
			}

			trace.append(false, "::::::: Adding a callListener to the terminal.\n\n");

			// get AGENT ID
			LucentTerminal agentTerminal = null;
			Agent[] agents = null;

			agentTerminal = (LucentTerminal) provider.getTerminal(callingExt); 
			agents = agentTerminal.getAgents(); 
			try
			{
				strAgentId = agents[0].getAgentID();
				trace.append(true, "::::::: AgentID " + agents[0].getAgentID() + "\n");
			}
			catch (Exception e)
			{
				trace.append("******Agent " + callingExt + " Not Loggedin" + "******\n\n");
			}
			
			// add callListener to terminal
			myTerminal.addCallListener(this);
			trace.append(true, "::::::: Waiting for Call on ext " + callingExt + "\n");
			trace.append(true, ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
			trace.append(true, "Finished Initialization\n");
			trace.append(true, ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n\n");

			
		} catch (Exception e) {
			
			trace.append("******login() caught " + e + "******\n");
			trace.append("******Message: " + e.getMessage() + "******\n\n");
			handleProviderShutdown();
			return;
			
		}
	}

	@Override
	public void terminalConnectionRinging(CallControlTerminalConnectionEvent arg0) {
		handleIncomingCall(arg0);
	}

	public String[] getServices() {
		String[] services = null;

		try {
			// get service providers on the network -- this depends on
			// the administration in the tsapi.pro file
			services = jtapiPeer.getServices();
		} catch (PlatformException e) {
			
			trace.append("JtapiPeer.getServices(): caught PlatformException\n");
			trace.append("Message: " + e.getMessage() + "\n\n");
			e.printStackTrace();
			return null;
			
		}
		return services;
	}
	
    /**
     * This method executes a Provider shutdown.
	 * It first verifies that the Provider is in service, it then removes the
	 * callListener that we have on the terminal, and then uses the shutdown method
	 * on the Provider to terminate session.
	 */
	public void logout() {
		if (provider != null) {

			if (provider.getState() == Provider.IN_SERVICE) {

				// remove callListener on terminal (nice but not necessary)
				if (myTerminal != null) {
					try {
						CallListener observers[] = myTerminal.getCallListeners();

						if (observers != null) {

							for (int i = 0; i < observers.length; i++) {
								myTerminal.removeCallListener(observers[i]);
							}
						}
					} catch (Exception e) {
						trace.append("logout() caught " + e + "\n");
						trace.append("Message: " + e.getMessage() + "\n\n");
						return;
					}
				}
				
				// shutdown Provider
				trace.append(false, "Shutting down provider...\n\n");
				try {
					provider.shutdown();
				} catch (Exception e) {
					trace.append("logout() caught " + e + "\n");
					trace.append("Message: " + e.getMessage() + "\n\n");
					return;
				}
			}
			provider = null;
		}
	}

	/**
	 * This method logs incoming calls.
	 * When a call has been received by our observed terminal, record:
	 *   - date and time call was received
	 *   - the calling device information
	 *   - UserToUserInfo received, if any,  by using the getUserToUserInfo method in the
	 *     LucentCallInfo interface.
	 * Note that this method will be called for two specific events:
	 *   - CallControlTermConnRingingEv - to track when calls are alerting at our observed
	 *                                    terminal
	 */
	public void handleIncomingCall(CallControlTerminalConnectionEvent event) {
		
		Address callingAddress;
		String uui = null;
		Date d = new Date();

		callingAddress = event.getCallingAddress();
		
		final TelephonyClient client = new TelephonyClient();
		
		if (callingAddress != null) {
			Connection[] connections = event.getCall().getConnections();
			TerminalConnection tConn = null;
			if (connections != null) {
				for (Connection conn : connections) {
					if (conn.getAddress().getName().equals(myAddress.getName())) {
						for (TerminalConnection tc : conn.getTerminalConnections()) {
							if (myTerminal.getName().equals(tc.getTerminal().getName())){
								tConn = tc;
								//clientConfigName set in config
								//agentId = Set in Initalize
								callId = Integer.toString(((ITsapiConnIDPrivate) conn).getTsapiConnectionID().getCallID());
								agentExtension = myAddress.getName();
								try {
									   java.net.InetAddress i = java.net.InetAddress.getLocalHost();
									   //System.out.println(i);                  // name and IP address
									   //System.out.println(i.getHostName());    // name
									   //System.out.println(i.getHostAddress()); // IP address only
									   localPartyIP = i.getHostAddress();
									   }
								catch(Exception e)
									{
									   e.printStackTrace();
									}
									 
								localPartyPort = 0;
								callDirection = "Inbound";
								callerLineIdentity = event.getCallingAddress().getName();
								callDetails(event);
								
								if(conn.getState() == 51) //Transfer
								{
									//disconnect();
									client.callDisconnected(callId);
									Date date = new Date();
									trace.append(dateFormat.format(date) + ": Tranfer - Disconnected......: " + callId + "\n");
									trace.append(true, ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n\n");
								}
								while (conn.getState() == 51) //TRANSFER CALL
								{
								}
								
								break;
							}
						}
					}
				}
				
				
				final TerminalConnection conn = tConn;
	
				try {
					Runnable r = new Runnable() {
					int callStatusInt = 0;
						public void run() {
							try {
								if (conn!=null){
									
									threadNum++;
									//System.out.println("tConn:" + conn.getTerminal().getName());
									
									///////RINGING
									if (conn.getState() == 65) 
									{
										Date date = new Date();
										trace.append(dateFormat.format(date) + ": New Call Coming In AID->" + strAgentId + " : ExID->" + agentExtension + " : CID->" + callId + " : TID->" + threadNum + "\n");
									}
									while (conn.getState() == 65)
									{
										//trace.append("Call Status3: Ringing: " + conn.getState() + "\n\n");
									}
									
									if(callStatusInt == 51) //Transfer
									{
										//disconnect();
										client.callDisconnected(callId);
										Date date = new Date();
										trace.append(dateFormat.format(date) + ": Tranfer - Disconnected......: " + callId + "\n");
										trace.append(true, ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n\n");
									}
									
									///////ACTIVE CALL
									if (conn.getState() == 67)
									{
										client.callConnect(clientConfigName, callId, strAgentId, agentExtension, localPartyIP, localPartyPort, callDirection, callerLineIdentity);
										Date date = new Date();
										trace.append(dateFormat.format(date) + ": Connected......: " + callId + "\n");
									}
									while (conn.getState() == 67) //ACTIVE CALL
									{
										callStatusInt = (int) callStatus(); 
										//trace.append("Active Call: " + callId + "\n\n");
										
										if(callStatusInt == 99) //Call Hold
										{
											client.callHold(callId);
											Date date = new Date();
											trace.append(dateFormat.format(date) + ": Hold......: " + callId + "\n");
										}
										while (callStatusInt == 99)
										{
											callStatusInt = (int) callStatus(); 
											if(callStatusInt != 99) //Call UnHold
											{
												client.callUnhold(callId);
												Date date = new Date();
												trace.append(dateFormat.format(date) + ": Unhold......: " + callId + "\n");
											}
										}
										if(callStatusInt == 51) //Transfer
										{
											//disconnect();
											client.callDisconnected(callId);
											Date date = new Date();
											trace.append(dateFormat.format(date) + ": Tranfer - Disconnected......: " + callId + "\n");
											trace.append(true, ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n\n");
										}
										if(callStatusInt == 0) //Call Hold
										{
											//disconnect();
											client.callDisconnected(callId);
											Date date = new Date();
											trace.append(dateFormat.format(date) + ": Disconnected......: " + callId + "\n");
											trace.append(true, ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n\n");
										}
										
									}
									
								}
								else{
									trace.append("Terminal Connection could not be found.\n");
								}
								
							} catch (Exception e) {
								trace.append("handleIncomingCall() thread caught "
												+ e + "\n");
								trace.append("Message: " + e.getMessage()
										+ "\n\n");
							} 
						}

					};
					new Thread(r).start();
				} catch (Exception e) {
					trace.append("handleIncomingCall() caught " + e + "\n");
					trace.append("Message: " + e.getMessage() + "\n\n");
					return;
				}
			}
		} else {
			//trace.append(DateFormat.getDateTimeInstance().format(d) + " Call received from: <Unknown>" + uui);
		}
	}

	/**
	 * This method does the necessary initialization for our application, 
	 * when the provider has been created and is in service.
	 */
	public synchronized void handleProviderInService() {
		notify();
		parent.handleProviderInService(provider.getName());
	}

	/**
	 * This method does the necessary clean-up for our application, 
	 * when the session has been terminated.
	 */
	public void handleProviderShutdown() {
		provider = null;
		parent.handleProviderShutdown();
	}
	
	/**
	 * This method disconnects active call (in the TALKING state).
	 * It uses the getTerminalConnections method of the Terminal interface.
	 * If there is only one terminalConnection, terminate that one.  Otherwise,
	 * get a handle on the connection that is in the TALKING state.  When a connection
	 * is found, then calls the disconnect(connection) method to end the call.
	 */
	public Object callStatus() {
		TerminalConnection[] terminalConnections = null;
		boolean found = false;
		Connection connection;
		int callStatus = 0;
		
		try {
			terminalConnections = myTerminal.getTerminalConnections();
		} catch (PlatformException e) {
			trace.append("Terminal.getTerminalConnections() caught PlatformException\n");
			trace.append("Message: " + e.getMessage() + "\n\n");
			e.printStackTrace();
		}

		if (terminalConnections != null) {

			if (terminalConnections.length == 1) {

				connection = terminalConnections[0].getConnection();

				for (int i = 0; i < terminalConnections.length; i++) {
					if (((CallControlTerminalConnection) terminalConnections[i]).getCallControlState() == CallControlTerminalConnection.TALKING) {

						found = true;
						connection = terminalConnections[i].getConnection();
						//trace.append(((CallControlTerminalConnection) terminalConnections[i]).getCallControlState() + "\n\n");
						callStatus = ((CallControlTerminalConnection) terminalConnections[i]).getCallControlState();
					}
					else
					{
						//trace.append(((CallControlTerminalConnection) terminalConnections[i]).getCallControlState() + "\n\n");
						callStatus = ((CallControlTerminalConnection) terminalConnections[i]).getCallControlState();
					}
				}
				if (!found) {
					//trace.append("There are no Connections in the Talking state\n\n");
					//trace.append(((CallControlTerminalConnection) terminalConnections[i]).getCallControlState() + "\n\n");
				}

			} else {
				for (int i = 0; i < terminalConnections.length; i++) {

					if (((CallControlTerminalConnection) terminalConnections[i]).getCallControlState() == CallControlTerminalConnection.TALKING) {

						found = true;
						connection = terminalConnections[i].getConnection();
						callStatus = ((CallControlTerminalConnection) terminalConnections[i]).getCallControlState();
					}
				}
				if (!found) {
					//trace.append("There are no Connections in the Talking state\n\n");
				}
			}
		}
		return callStatus;
	}
	
	
	/**
	 * This method disconnects active call (in the TALKING state).
	 * It uses the getTerminalConnections method of the Terminal interface.
	 * If there is only one terminalConnection, terminate that one.  Otherwise,
	 * get a handle on the connection that is in the TALKING state.  When a connection
	 * is found, then calls the disconnect(connection) method to end the call.
	 */
	public void disconnect() {
		TerminalConnection[] terminalConnections = null;
		boolean found = false;
		Connection connection;
		
		try {
			terminalConnections = myTerminal.getTerminalConnections();
		} catch (PlatformException e) {
			trace.append("Terminal.getTerminalConnections() caught PlatformException\n");
			trace.append("Message: " + e.getMessage() + "\n\n");
			e.printStackTrace();
		}

		if (((CallControlTerminalConnection) terminalConnections[0])
				.getCallControlState() == CallControlTerminalConnection.HELD) {
			
		
		trace.append(((CallControlTerminalConnection) terminalConnections[0])
				.getState() + "Inside Disconnet\n\n");
		
		}
		else
		{
			trace.append(((CallControlTerminalConnection) terminalConnections[0])
					.getState() + "Inside Disconnet2\n\n");
		}
		if (terminalConnections != null) {

			if (terminalConnections.length == 1) {

				connection = terminalConnections[0].getConnection();
				//disconnect(connection);

			} else {
				for (int i = 0; i < terminalConnections.length; i++) {

					if (((CallControlTerminalConnection) terminalConnections[i])
							.getCallControlState() == CallControlTerminalConnection.TALKING) {

						found = true;
						connection = terminalConnections[i].getConnection();

						//disconnect(connection);
						trace.append("Inside Disconnet\n\n");
						
					}
				}
				if (!found) {
					trace.append("There are no Connections in the Talking state\n\n");
				}
			}
		}
	}

	/**
	 * This method disconnects the connection passed in the argument.
	 * It uses the disconnect method of the Connection interface to end the call.
	 */
	public void disconnect(Connection connection) {
		if (connection != null) {

			try {
				connection.disconnect();
			} catch (Exception e) {
				trace.append("Connection.disconnect() caught " + e + "\n");
				trace.append("Message: " + e.getMessage() + "\n\n");
				return;
			}
		}
	}

	public void providerEventTransmissionEnded(ProviderEvent event) {
		// NO-OP
	}

	public void providerInService(ProviderEvent event) {
		handleProviderInService();
	}

	public void providerOutOfService(ProviderEvent event) {
		// NO-OP		
	}

	public void providerShutdown(ProviderEvent event) {
		handleProviderShutdown();
	}
	
	
	public void callDetails(CallControlTerminalConnectionEvent event) {

	}	
}
