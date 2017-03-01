package route;

import javax.swing.JTextArea;
import javax.telephony.callcenter.RouteCallback;
import javax.telephony.callcenter.RouteSession;
import javax.telephony.callcenter.events.ReRouteEvent;
import javax.telephony.callcenter.events.RouteCallbackEndedEvent;
import javax.telephony.callcenter.events.RouteEndEvent;
import javax.telephony.callcenter.events.RouteEvent;
import javax.telephony.callcenter.events.RouteUsedEvent;

import route.ui.DestinationDialog;
import route.ui.RouteFrame;

class MyRouteCallback implements RouteCallback {
	private final JTextArea trace;
	private final RouteService service;
	private final RouteFrame frame;
	public MyRouteCallback(RouteService service, JTextArea traceArea, RouteFrame frame) {
		trace = traceArea;
		this.service = service;
		this.frame = frame;
	}

	public void routeEvent(RouteEvent event) {
		trace.append("\nReceived Route Event\n");
		trace.append("\tcallingAddress="
				+ event.getCallingAddress().getName() + "\n");
		trace.append("\tcallingTerminal="
				+ event.getCallingTerminal().getName() + "\n");
		trace.append("\tcurrentRouteAddress="
				+ event.getCurrentRouteAddress().getName() + "\n");
		trace.append("\trouteSelectAlgorithm="
				+ event.getRouteSelectAlgorithm() + "\n");
		trace.append("\tsetupInformation=" + event.getSetupInformation()
				+ "\n");

		service.setEvent(event);
		new DestinationDialog(frame, "Route Destination","Please select a destination to route to.");
	}

	public void reRouteEvent(ReRouteEvent event) {
		trace.append("\nReceived reRoute Event\n");
		if (event.getRouteSession() != null) {
			trace.append("\tRouteSession state="
					+ RouteService.stateToString(event
							.getRouteSession().getState()) + "\n\n");
		} else {
			trace.append("\tRouteSession is null\n\n");
		}

		service.setEvent(event);
		new DestinationDialog(frame, "Route Destination","Please select a destination to re-route to.");
	}

	public void routeUsedEvent(RouteUsedEvent event) {
		trace.append("\nReceived Route Used Event\n");
		trace.append("\tcallingAddress="
				+ event.getCallingAddress().getName() + "\n");
		trace.append("\tcallingTerminal="
				+ event.getCallingTerminal().getName() + "\n");
		trace.append("\tdomain=" + event.getDomain() + "\n");
		trace
				.append("\trouteUsed=" + event.getRouteUsed().getName()
						+ "\n");
	}

	public void routeEndEvent(RouteEndEvent event) {
		trace.append("\nReceived Route End Event\n");
		RouteSession activeSession = event.getRouteSession();
		if (activeSession != null) {
			trace.append("\tRouteSession state="
					+ RouteService.stateToString(activeSession
							.getState()) + "\n");
		} else {
			trace.append("\tRouteSession is null\n");
		}
		frame.disableButton();
	}

	public void routeCallbackEndedEvent(RouteCallbackEndedEvent event) {
		trace.append("\nReceived Route Callback Ended Event\n");
		trace.append("\tRouteAddress=" + event.getRouteAddress().getName()
				+ "\n");
	}
}
