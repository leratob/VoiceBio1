package route;

import javax.swing.JTextArea;
import javax.telephony.Address;
import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.Provider;
import javax.telephony.ProviderEvent;
import javax.telephony.ProviderListener;
import javax.telephony.callcenter.RouteAddress;
import javax.telephony.callcenter.RouteSession;
import javax.telephony.callcenter.events.RouteEvent;
import javax.telephony.callcenter.events.RouteSessionEvent;

import route.ui.RouteFrame;

public class RouteService implements ProviderListener {
	private RouteData data;

	private final JTextArea area;

	private Provider provider;

	private RouteAddress myRouteAddress;

	private MyRouteCallback myRouteCallback;

	private RouteSessionEvent event = null; //either RouteEvent or ReRouteEvent
	
	public RouteService(RouteData data, JTextArea area) {
		this.data = data;
		this.area = area;
	}

	public String[] getServices()  throws Exception{
		try{
			//create JtapiPeer
			final JtapiPeer jtapiPeer = JtapiPeerFactory
				.getJtapiPeer(isNull(data.peer) ? null : data.peer);
			return jtapiPeer.getServices();
		} catch (Exception e) {
			area.append("init() caught exception " + e.getClass().getName()
					+ "\n");
			area.append("Message: " + e.getMessage() + "\n\n");
			area.append("Provider could not be created.\n Please verify that parameters " +
					"in the page are correct.\n\n");
			throw e;
		}
	}
	
	public void login() throws Exception{
		try {
			String providerString = data.service + ";loginID=" + data.login
					+ ";passwd=" + data.password;
			// create JtapiPeer
			JtapiPeer jtapiPeer = JtapiPeerFactory
					.getJtapiPeer(isNull(data.peer) ? null : data.peer);

			// create Provider
			provider = jtapiPeer.getProvider(providerString);
			area.append("Provider created successfully.\n\n");
			area.append("Waiting for the provider to initialize...\n");

			// add a ProviderListener to the Provider to be notified when it is
			// in service
			provider.addProviderListener(this);

			// wait to be notified when the Provider is in service --
			// corresponding notify is in the providerChangedEvent() method
			synchronized(this){
				wait();
			}
			area.append("Provider is in service.\n\n");
		} catch (Exception e) {
			area.append("init() caught exception " + e.getClass().getName()
					+ "\n");
			area.append("Message: " + e.getMessage() + "\n\n");
			area.append("Provider could not be created.\n Please verify that parameters " +
					"in the page are correct.\n\n");
			throw e;
		}
	}

	public void registerRouteCallback(RouteFrame frame) {
		Address myAddress;
		if (provider != null) {
			try {
				// create address object for routing VDN
				// this VDN must be administered in the switch with an
				// 'adjunct routing' step followed by a wait step
				myAddress = provider.getAddress(data.vdn);

				if (!(myAddress instanceof RouteAddress)) {
					area.append("Address " + myAddress.getName()
							+ " is not instance of RouteAddress\n");
					return;
				}

				myRouteAddress = (RouteAddress) myAddress;

				// create the Route Callback object (class is defined below)
				myRouteCallback = new MyRouteCallback(this, area, frame);

				// register callback to route calls for myRouteAddress
				myRouteAddress.registerRouteCallback(myRouteCallback);
				area.append("Registered Route Callback with "
						+ myRouteAddress.getName() + ".\n");
				area.append("Waiting for calls (please place a call to VDN "
						+ data.vdn + ")...\n");

			} catch (Exception e) {
				area.append("start() caught exception "
						+ e.getClass().getName() + "\n");
				area.append("Message: " + e.getMessage() + "\n\n");
			}
		}
	}

	public void cancelRouteCallback() {
		if (myRouteAddress != null) {

			try {
				// cancel registration to route calls for myRouteAddress
				myRouteAddress.cancelRouteCallback(myRouteCallback);
				area.append("\nCancelled Route Callback for "
						+ myRouteAddress.getName() + "\n");
			} catch (Exception e) {
				area.append("stop() caught exception " + e.getClass().getName()
						+ "\n");
				area.append("Message: " + e.getMessage() + "\n\n");
			}
		}
	}

	public void handleRouteRequest(String destination) {
		if(event instanceof RouteEvent){
			RouteEvent routeEvent = (RouteEvent)event;
			// setup routeSelected array, which contains one destination
			String[] routeSelected = new String[1];
			routeSelected[0] = destination;
	
			try {
				area.append("\tcause=" + routeEvent.getRouteSession().getCause()
						+ "\n");
				area.append("\trouteAddress="
						+ routeEvent.getRouteSession().getRouteAddress().getName()
						+ "\n");
				area.append("\tstate="
						+ stateToString(routeEvent.getRouteSession().getState())
						+ "\n");
				area.append("\tSelected route: " + routeSelected[0] + "\n");
				routeEvent.getRouteSession().selectRoute(routeSelected);
	
				RouteSession[] activeSessions = routeEvent.getCurrentRouteAddress()
						.getActiveRouteSessions();
	
				if (activeSessions != null) {
	
					for (int i = 0; i < activeSessions.length; i++) {
						area.append("\tRouteSession[" + i + "] state="
								+ stateToString(activeSessions[i].getState())
								+ "\n");
					}
	
				} else {
	
					area.append("\tRouteSession is null\n");
				}
				area.append("\n");
	
			} catch (Exception e) {
	
				area.append("handleRouteRequest() caught exception "
						+ e.getClass().getName() + "\n");
				area.append("Message: " + e.getMessage() + "\n\n");
				e.printStackTrace();
			}
		}
	}

	static String stateToString(int state) {
		String strState;

		switch (state) {
		case RouteSession.RE_ROUTE:
			strState = "RE_ROUTE";
			break;
		case RouteSession.ROUTE:
			strState = "ROUTE";
			break;
		case RouteSession.ROUTE_CALLBACK_ENDED:
			strState = "ROUTE_CALLBACK_ENDED";
			break;
		case RouteSession.ROUTE_END:
			strState = "ROUTE_END";
			break;
		case RouteSession.ROUTE_USED:
			strState = "ROUTE_USED";
			break;
		default:
			strState = "Other: " + state;
			break;
		}
		return strState;
	}

	private boolean isNull(String peer) {
		return (peer == null || "".equals(peer));
	}

	public void providerInService(ProviderEvent arg0) {
	    area.append("providerInService event received on ProviderListener\n");
	    synchronized(this){
            notify(); // registerRouteCallback() is waiting on this event
        }
    }
	public void providerEventTransmissionEnded(ProviderEvent arg0) {
	    area.append("providerEventTransmissionEnded event received on ProviderListener\n");
	}
	public void providerOutOfService(ProviderEvent arg0) {
	    area.append("providerOutOfService event received on ProviderListener\n");
	}
	
	public void providerShutdown(ProviderEvent arg0) {
	    area.append("providerShutdown event received on ProviderListener\n");
	}

	public void updateData(RouteData routeData) {
		this.data = routeData;
	}

	public void setEvent(RouteSessionEvent event) {
		this.event = event;
		
	}
	
	public void shutdown(){
		if (provider!=null){
			provider.shutdown();
			provider = null;
		}
	}
	
	
}
