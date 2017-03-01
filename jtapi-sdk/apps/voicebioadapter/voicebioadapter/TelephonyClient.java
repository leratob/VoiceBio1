package voicebioadapter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.tempuri.SoapTelephonyService;
import org.tempuri.SoapTelephonyService_Service;

public class TelephonyClient {
	
	/*
	public static void main(String[] args) {
		URL url;
		try {
			url = new URL("http://10.18.148.126:8000/?singlewsdl");
			QName qname = new QName("http://tempuri.org/","SoapTelephonyService");
			
			//url = new File("c:/test.wsdl").toURL();
			//QName qname = new QName("http://tempuri.org/","SoapTelephonyService");
			
			Service service= SoapTelephonyService_Service.create(url, qname);
			SoapTelephonyService SoapTelephonyService = service.getPort(SoapTelephonyService.class);
			String clientConfigName = "test"; 
			String callId = "test";
			String agentId = "test";
			String agentExtension =  "test";
			String localPartyIP = "test";
			Integer localPartyPort = 1;
			String callDirection =  "test";
			String callerLineIdentity =  "test";
			SoapTelephonyService.callConnected(clientConfigName, callId, agentId, agentExtension, localPartyIP, localPartyPort, callDirection, callerLineIdentity);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/

		public void callConnect(String clientConfigName,String callId, String agentId, String agentExtension, String localPartyIP, Integer localPartyPort, String callDirection, String callerLineIdentity) {
			URL url;
			try {
				url = new URL("http://win-rehbckn8jie:8000/?singlewsdl");
				QName qname = new QName("http://tempuri.org/","SoapTelephonyService");				
				//url = new File("c:/test.wsdl").toURL();
				//QName qname = new QName("http://tempuri.org/","SoapTelephonyService");
				
				Service service= SoapTelephonyService_Service.create(url, qname);
				SoapTelephonyService SoapTelephonyService = service.getPort(SoapTelephonyService.class);
				/*
				clientConfigName = "test";
				callId = "test";
				agentId = "test";
				agentExtension =  "test";
				localPartyIP = "test";
				localPartyPort = 1;
				callDirection =  "test";
				callerLineIdentity =  "test";
				*/
				SoapTelephonyService.callConnected(clientConfigName, callId, agentId, agentExtension, localPartyIP, localPartyPort, callDirection, callerLineIdentity);
				System.out.println("Connected......: " + callId);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		public void callDisconnected(String callId) {
			URL url;
			try {
				url = new URL("http://win-rehbckn8jie:8000/?singlewsdl");
				QName qname = new QName("http://tempuri.org/","SoapTelephonyService");				
				//url = new File("c:/test.wsdl").toURL();
				//QName qname = new QName("http://tempuri.org/","SoapTelephonyService");
				
				Service service= SoapTelephonyService_Service.create(url, qname);
				SoapTelephonyService SoapTelephonyService = service.getPort(SoapTelephonyService.class);
				/*
				clientConfigName = "test";
				callId = "test";
				agentId = "test";
				agentExtension =  "test";
				localPartyIP = "test";
				localPartyPort = 1;
				callDirection =  "test";
				callerLineIdentity =  "test";
				*/
				SoapTelephonyService.callDisconnected(callId);
				System.out.println("Disconnecting......: " + callId);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void callHold(String callId) {
			URL url;
			try {
				url = new URL("http://win-rehbckn8jie:8000/?singlewsdl");
				QName qname = new QName("http://tempuri.org/","SoapTelephonyService");				
				//url = new File("c:/test.wsdl").toURL();
				//QName qname = new QName("http://tempuri.org/","SoapTelephonyService");
				
				Service service= SoapTelephonyService_Service.create(url, qname);
				SoapTelephonyService SoapTelephonyService = service.getPort(SoapTelephonyService.class);
				/*
				clientConfigName = "test";
				callId = "test";
				agentId = "test";
				agentExtension =  "test";
				localPartyIP = "test";
				localPartyPort = 1;
				callDirection =  "test";
				callerLineIdentity =  "test";
				*/
				SoapTelephonyService.callHold(callId);
				System.out.println("Hold......: " + callId);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void callUnhold(String callId) {
			URL url;
			try {
				url = new URL("http://win-rehbckn8jie:8000/?singlewsdl");
				QName qname = new QName("http://tempuri.org/","SoapTelephonyService");				
				//url = new File("c:/test.wsdl").toURL();
				//QName qname = new QName("http://tempuri.org/","SoapTelephonyService");
				
				Service service= SoapTelephonyService_Service.create(url, qname);
				SoapTelephonyService SoapTelephonyService = service.getPort(SoapTelephonyService.class);
				/*
				clientConfigName = "test";
				callId = "test";
				agentId = "test";
				agentExtension =  "test";
				localPartyIP = "test";
				localPartyPort = 1;
				callDirection =  "test";
				callerLineIdentity =  "test";
				*/
				SoapTelephonyService.callUnHold(callId);
				System.out.println("Unhold......: " + callId);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
