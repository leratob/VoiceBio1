
package acd;

import javax.telephony.Address;
import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.JtapiPeerUnavailableException;
import javax.telephony.Provider;
import javax.telephony.ProviderEvent;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.ACDAddressEvent;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.AgentTerminal;
import javax.telephony.callcenter.CallCenterProvider;

import acd.ui.TraceOutputUi;

import com.avaya.jtapi.tsapi.LucentAgent;
import com.avaya.jtapi.tsapi.LucentAgentStateInfo;
import com.avaya.jtapi.tsapi.adapters.ACDAddressListenerAdapter;
import com.avaya.jtapi.tsapi.adapters.ProviderListenerAdapter;

public class ACD extends ProviderListenerAdapter implements ACDSampleAppInterface {

    private String acd;
    private String agent1 = null;
    private String agent2 = null;
    private Provider provider;
    private Address myAddress;
    private String service;
    private String login;
    private String password ;
    private String jtapiClass;
    private String providerString;
    private String agentOneStation,agentTwoStation;
    
    private JtapiPeer jtapiPeer;
    TraceOutputUi trace = null;/* new MyTextArea(); */
	private String agent1Password;
	private String agent2Password;

    public ACD(TraceOutputUi traceOutput){
        trace = traceOutput;
        
    }

    public synchronized void init() {
        
        providerString = service + ";login=voicebio;passwd=Voicebio1#";
        //providerString = service + ";login=" + login + ";passwd="
        //        + password;
        
        
        try {
            if (jtapiPeer == null){
                jtapiPeer = JtapiPeerFactory.getJtapiPeer(jtapiClass);
                String[] services = jtapiPeer.getServices();
                if (services == null) {
                    System.out.println("Services array is null");
                    System.exit(0);
                }
            }
            provider = jtapiPeer.getProvider(providerString);
            trace.append("Provider created successfully.\n\n");
            trace.append("Waiting for the provider to initialize...\n");

            //add a Provider listener to the provider
            provider.addProviderListener(this);
           
        } catch (Exception e) {
            e.printStackTrace();
            trace.append("init() caught exception " + e.getClass().getName()
                    + "\n");
            trace.append("Message: " + e.getMessage() + "\n\n");
            trace
                    .append("Provider could not be created.\nVerify parameters in the HTML page are correct.\n\n");
        }

    }

    public void start() {
        ACDAddress[] acdAddresses = null;
        Agent[] agents = null;
        ACDAddress myACDAddress = null;
        Agent myAgent1 = null;
        Agent myAgent2 = null;
        AgentTerminal myAgent1Terminal = null;
        AgentTerminal myAgent2Terminal = null;
        Address myAgent1Address = null;
        Address myAgent2Address = null;

        if (provider != null) {

            try {

                if (provider instanceof CallCenterProvider) {

                    try {
                        acdAddresses = ((CallCenterProvider) provider)
                                .getACDAddresses();
                    } catch (Exception e) {
                        trace.append("Could not obtain ACDAddresses from Provider.\n\n");
                        trace.append(e.getMessage());
                    }

                    if (acdAddresses != null) {

                        trace.append("ACDAddresses for this provider:" + "\n");

                        for (int i = 0; i < acdAddresses.length; i++) {
                            trace.append("\tACDAddress="
                                    + acdAddresses[i].getName() + "\n");
                        }

                        trace.append("\n");

                        for (int i = 0; i < acdAddresses.length; i++) {

                            try {
                                agents = acdAddresses[i].getLoggedOnAgents();
                            } catch (Exception e) {
                                trace
                                        .append("Could not obtain logged on agents.\n\n");
                            }

                            if (agents != null) {

                                trace.append("Agents at "
                                        + acdAddresses[i].getName() + ":\n");
                                for (int k = 0; k < agents.length; k++) {
                                    trace.append("\tAgent["
                                            + k
                                            + "]="
                                            + agents[k].getAgentTerminal()
                                                    .getName() + "\n");
                                }
                                trace.append("\n");
                            } else {
                                trace.append("Agents at "
                                        + acdAddresses[i].getName()
                                        + ": <none>\n\n");
                            }
                        }
                    } else {
                        trace
                                .append("ACDAddresses for this provider: <none>\n\n");
                    }
                }

                try {
                    myAddress = provider.getAddress(acd);
                } catch (Exception e) {
                    trace.append("Please verify ACD address is correct.\n\n");
                    throw (e);
                }

                // if Address returned does not implement ACDAddress return
                if (!(myAddress instanceof ACDAddress)) {
                    trace.append("Address" + myAddress.getName()
                            + "doesn't implement ACDAddress\n\n");
                    return;
                }

                myACDAddress = (ACDAddress) myAddress;

                agents = null;
                try {
                    agents = myACDAddress.getLoggedOnAgents();
                } catch (Exception e) {
                    trace.append("Could not obtain logged on agents.\n\n");
                    throw (e);
                }

                trace.append("Agents/States currently at "
                        + myACDAddress.getName() + ":\n");

                if (agents != null) {
                    for (int i = 0; i < agents.length; i++) {
                        trace.append("\tAgent[" + i + "]="
                                + agents[i].getAgentTerminal().getName()
                                + " State: "
                                + stateToString(agents[i].getState()) + "\n");
                    }
                    trace.append("\n");
                } else {
                    trace
                            .append("There are no agents currently logged in.\n\n");
                }

                //add listener on ACD address
                myAddress.addAddressListener(new MyACDListener(trace));

                trace
                        .append("Trying to log in agents: " + agent1 + ", "
                                + agent2 + " to ACD " + myAddress.getName()
                                + "...\n\n");

                myAgent1Address = provider.getAddress(agentOneStation);
                myAgent2Address = provider.getAddress(agentTwoStation);
                myAgent1Terminal = (AgentTerminal) provider.getTerminal(agentOneStation);
                myAgent2Terminal = (AgentTerminal) provider.getTerminal(agentTwoStation);

                // log agents in using AgentTerminal.addAgent()
                // Note that in a skills environment, the ACDAddress maybe

                if (myAgent1Terminal != null) {

                    try {
                        myAgent1 = myAgent1Terminal.addAgent(myAgent1Address, // Agent
                                                                                // 's
                                                                                // address
                                myACDAddress, // ACD's address
                                Agent.LOG_IN, // Agent's initial state
                                agent1, // Agent ID
                                agent1Password); // Agent password
                    } catch (Exception e) {
                        trace.append("Unable to log in agent " + agent1
                                + ".\n\n");
                        e.printStackTrace();
                    }
                } else {
                    trace.append("Agent terminal for " + agent1
                            + "is null.\n\n");
                }

                if (myAgent2Terminal != null) {

                    try {
                        myAgent2 = myAgent2Terminal.addAgent(myAgent2Address, // Agent
                                                                                // 's
                                                                                // address
                                myACDAddress, // ACD's address
                                Agent.LOG_IN, // Agent's initial state
                                agent2, // Agent ID
                                agent2Password); // Agent password
                    } catch (Exception e) {
                        trace.append("Unable to log in agent " + agent2
                                + ".\n\n");
                    }
                } else {
                    trace.append("Agent terminal for " + agent2
                            + "is null.\n\n");
                }

                if (myACDAddress != null) {

                    try {
                        agents = myACDAddress.getLoggedOnAgents();
                    } catch (Exception e) {
                        trace.append("Could not obtain logged in agents.\n\n");
                    }

                    Thread.sleep(2000);
                    trace.append("Agents/State/WorkMode currently at "
                            + myACDAddress.getName() + ":\n");
                    if (agents != null) {

                        for (int i = 0; i < agents.length; i++) {
                            LucentAgentStateInfo stateInfo = ((LucentAgent) agents[i])
                                    .getStateInfo();

                            if ((stateInfo.state != Agent.LOG_OUT)
                                    && (stateInfo.workMode != LucentAgent.MODE_AUTO_IN)) {

                                try {
                                    ((LucentAgent) agents[i]).setState(
                                            Agent.READY,
                                            LucentAgent.MODE_AUTO_IN);
                                } catch (Exception e) {
                                    trace
                                            .append("Could not set agent state.\n\n");
                                }
                            }
                            stateInfo = ((LucentAgent) agents[i])
                                    .getStateInfo();
                            trace.append("\tAgent[" + i + "]="
                                    + agents[i].getAgentTerminal().getName()
                                    + "\tState: "
                                    + stateToString(stateInfo.state)
                                    + "\tWorkMode: "
                                    + modeToString(stateInfo.workMode) + "\n");
                        }
                    }
                    trace.append("\n");

                    trace.append("Trying to log out agents: " + agent1 + ", "
                            + agent2 + " from ACD " + myAddress.getName()
                            + "...\n\n");

                    if (myAgent1 != null) {
                        myAgent1Terminal.removeAgent(myAgent1);
                    }
                    if (myAgent2 != null) {
                        myAgent2Terminal.removeAgent(myAgent2);
                    }
                } else {
                    trace.append("ACD address for " + acd + "is null.\n\n");
                }
            } catch (Exception e) {
                trace.append("start() caught " + e + "\n");
                trace.append("Message: " + e.getMessage() + "\n\n");
            }
            trace.append("Finished executing.Shutting down provider now");
            provider.shutdown();
        }
    }

    public String stateToString(int state) {

        String strState;

        switch (state) {
        case (Agent.LOG_IN):
            strState = "LOG_IN";
            break;
        case (Agent.LOG_OUT):
            strState = "LOG_OUT";
            break;
        case (Agent.NOT_READY):
            strState = "NOT_READY";
            break;
        case (Agent.READY):
            strState = "READY";
            break;
        case (Agent.WORK_NOT_READY):
            strState = "WORK_NOT_READY";
            break;
        case (Agent.WORK_READY):
            strState = "WORK_READY";
            break;
        case (Agent.BUSY):
            strState = "BUSY";
            break;
        case (Agent.UNKNOWN):
            strState = "UNKNOWN";
            break;
        default:
            strState = "Other: " + state;
            break;
        }
        return strState;
    }

    public static String modeToString(int mode) {

        String strMode;

        switch (mode) {
        case LucentAgent.MODE_AUTO_IN:
            strMode = "MODE_AUTO_IN";
            break;
        case LucentAgent.MODE_MANUAL_IN:
            strMode = "MODE_MANUAL_IN";
            break;
        case LucentAgent.MODE_NONE:
            strMode = "MODE_NONE";
            break;
        default:
            strMode = "Other: " + mode;
            break;
        }
        return strMode;
    }

    /* (non-Javadoc)
     * @see com.avaya.jtapi.tsapi.adapters.ProviderListenerAdapter#providerInService(javax.telephony.ProviderEvent)
     */
    @Override
    public void providerInService(ProviderEvent event) {
        trace.append("Provider is in service.\n\n");
    }

    public String[] getServices(String jtapiPeerName) throws JtapiPeerUnavailableException {
        jtapiPeer = JtapiPeerFactory.getJtapiPeer(jtapiPeerName);
        String[] services = jtapiPeer.getServices();
        if (services == null) {
            System.out.println("Services array is null");
            System.exit(0);
        }
        return services;
    }

    public void setInitializationParameters(ACDSampleInitializationParameters initParams) throws Exception {
        this.acd = initParams.getAcdAddress();
        this.agent1 = initParams.getAgentOneAddress();
        this.agent2 = initParams.getAgentTwoAddress();
        this.agent1Password = initParams.getAgentOnePassword();
        this.agent2Password = initParams.getAgentTwoPassword();
        this.jtapiClass = initParams.getJtapiClassName();
        this.login = initParams.getUserName();
        this.password = initParams.getPassword();
        this.service = initParams.getServiceName();
        this.agentOneStation = initParams.getAgentOneStation();
        this.agentTwoStation = initParams.getAgentTwoStation();
    }
}

class MyACDListener extends ACDAddressListenerAdapter{
    private TraceOutputUi trace;

    /**
     * @param trace
     */
    public MyACDListener(TraceOutputUi trace) {
        super();
        this.trace = trace;
    }

    /* (non-Javadoc)
     * @see com.avaya.jtapi.tsapi.adapters.ACDAddressListenerAdapter#acdAddressBusy(javax.telephony.callcenter.ACDAddressEvent)
     */
    @Override
    public void acdAddressBusy(ACDAddressEvent event) {
        trace.append("Received ACD Addr Busy Ev - Agent: " + event.getAgent().getAgentTerminal().getName() + "\n");
    }

    /* (non-Javadoc)
     * @see com.avaya.jtapi.tsapi.adapters.ACDAddressListenerAdapter#acdAddressLoggedOff(javax.telephony.callcenter.ACDAddressEvent)
     */
    @Override
    public void acdAddressLoggedOff(ACDAddressEvent event) {
        trace.append("Received ACD Addr Logged Off Ev - Agent: " + event.getAgent().getAgentTerminal().getName() + "\n");
    }

    /* (non-Javadoc)
     * @see com.avaya.jtapi.tsapi.adapters.ACDAddressListenerAdapter#acdAddressLoggedOn(javax.telephony.callcenter.ACDAddressEvent)
     */
    @Override
    public void acdAddressLoggedOn(ACDAddressEvent event) {
        trace.append("Received ACD Addr Logged On Ev - Agent: " + event.getAgent().getAgentTerminal().getName() + "\n");
    }

    /* (non-Javadoc)
     * @see com.avaya.jtapi.tsapi.adapters.ACDAddressListenerAdapter#acdAddressNotReady(javax.telephony.callcenter.ACDAddressEvent)
     */
    @Override
    public void acdAddressNotReady(ACDAddressEvent event) {
        trace.append("Received ACD Not Ready Ev - Agent: " + event.getAgent().getAgentTerminal().getName() + "\n");
    }

    /* (non-Javadoc)
     * @see com.avaya.jtapi.tsapi.adapters.ACDAddressListenerAdapter#acdAddressReady(javax.telephony.callcenter.ACDAddressEvent)
     */
    @Override
    public void acdAddressReady(ACDAddressEvent event) {
        trace.append("Received ACD Addr Ready Ev - Agent: " + event.getAgent().getAgentTerminal().getName() + "\n");
    }

    /* (non-Javadoc)
     * @see com.avaya.jtapi.tsapi.adapters.ACDAddressListenerAdapter#acdAddressUnknown(javax.telephony.callcenter.ACDAddressEvent)
     */
    @Override
    public void acdAddressUnknown(ACDAddressEvent event) {
        trace.append("Received ACD Addr Unknown Ev - Agent: " + event.getAgent().getAgentTerminal().getName() + "\n");
    }

    /* (non-Javadoc)
     * @see com.avaya.jtapi.tsapi.adapters.ACDAddressListenerAdapter#acdAddressWorkNotReady(javax.telephony.callcenter.ACDAddressEvent)
     */
    @Override
    public void acdAddressWorkNotReady(ACDAddressEvent event) {
        trace.append("Received ACD Addr Work Not Ready Ev - Agent: " + event.getAgent().getAgentTerminal().getName() + "\n");
    }

    /* (non-Javadoc)
     * @see com.avaya.jtapi.tsapi.adapters.ACDAddressListenerAdapter#acdAddressWorkReady(javax.telephony.callcenter.ACDAddressEvent)
     */
    @Override
    public void acdAddressWorkReady(ACDAddressEvent event) {
        trace.append("Received ACD Addr Work Ready Ev - Agent: " + event.getAgent().getAgentTerminal().getName() + "\n");
    }
}