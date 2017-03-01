/**
 * 
 */
package acd.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.telephony.JtapiPeerUnavailableException;

import acd.ACD;
import acd.ACDSampleAppInterface;
import acd.ACDSampleInitializationParameters;
import acd.ACDSampleParameters;


/**
 * @author sswamy
 *
 */
public class ACDFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	public static final String GET_SERVICES_COMMAND = "Get Services";
	public static final String RUN_COMMAND = "Run";

	//	private JFrame theFrame;
	private JButton getServicesButton,runButton;
	private TraceOutputUi logTextArea;
	private JLabel jtapiClassNameLabel, usernameLabel, passwordLabel, acdAddressLabel, 
		agentOneLabel, agentTwoLabel,agentOnePLabel, agentTwoPLabel, stationOneLabel,stationTwoLabel;
	private JTextField jtapiClassNameTField, userNameTField,acdAddressTField,agentOneTField,
		agentTwoTField,stationOneTField,stationTwoTField;
	private JPasswordField agentOnePField, agentTwoPField;
	private JPasswordField passwordTField;
	private JComboBox servicesComboBox;
	private ACDSampleAppInterface acdSampleApp;

	public ACDFrame(String title) {
		super(title);
	}

	public void init(){
		//construct all ui components
		//		theFrame = new JFrame("JTAPI ACD sample application");
		getServicesButton = new JButton("Get Services");
		runButton = new JButton("Run");
		logTextArea = new MyTextArea(10,80);

		jtapiClassNameLabel = new JLabel("JTAPI class name");
		usernameLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		acdAddressLabel = new JLabel("ACD address");
		agentOneLabel = new JLabel("Agent 1");
		agentTwoLabel = new JLabel("Agent 2");
		agentOnePLabel = new JLabel("Agent 1 Password");
		agentTwoPLabel = new JLabel("Agent 2 Password");
		stationOneLabel = new JLabel("Agent 1 Station");
		stationTwoLabel = new JLabel("Agent 2 Station");

		jtapiClassNameTField = new JTextField(80);
		userNameTField = new JTextField(80);
		passwordTField = new JPasswordField(80);
		acdAddressTField = new JTextField(80);
		agentOneTField = new JTextField(80);
		agentTwoTField = new JTextField(80);
		stationOneTField = new JTextField(80);
		stationTwoTField = new JTextField(80);
		agentOnePField = new JPasswordField(80);
		agentTwoPField = new JPasswordField(80);
		
		servicesComboBox = new JComboBox();

		//business logic for UI
		JPanel panel1 = new JPanel(new GridLayout(11,2)); 
		panel1.add(usernameLabel);
		panel1.add(userNameTField);

		panel1.add(passwordLabel);
		panel1.add(passwordTField);

		panel1.add(jtapiClassNameLabel);
		panel1.add(jtapiClassNameTField);

		panel1.add(getServicesButton);
		panel1.add(servicesComboBox);

		panel1.add(acdAddressLabel);
		panel1.add(acdAddressTField);

		panel1.add(agentOneLabel);
		panel1.add(agentOneTField);
		
		panel1.add(agentOnePLabel);
		panel1.add(agentOnePField);
		
		panel1.add(stationOneLabel);
		panel1.add(stationOneTField);

		panel1.add(agentTwoLabel);
		panel1.add(agentTwoTField);
		
		panel1.add(agentTwoPLabel);
		panel1.add(agentTwoPField);
		
		panel1.add(stationTwoLabel);
		panel1.add(stationTwoTField);

		JScrollPane scrollPane = new JScrollPane((JTextArea)logTextArea);

		JPanel panel2 = new JPanel(new FlowLayout());

		panel2.add(runButton);
		panel2.add(scrollPane);
		//panel2.setBounds(0, 0, 500, 200);
		getServicesButton.addActionListener(this);
		runButton.addActionListener(this);

		jtapiClassNameTField.setText("com.avaya.jtapi.tsapi.TsapiPeer");
		//add panels to frame
		this.setLayout(new GridLayout(3,1));
		this.add(panel1);
		this.setBounds(0, 0, 500, 550);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.add(panel2);
		this.add(scrollPane);

		this.setVisible(true);
		//pack();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ACDFrame acdFrame = new ACDFrame("JTAPI ACD sample application");
		acdFrame.init();
		acdFrame.setSampleApp(new ACD(acdFrame.getLogTextArea()));
	}

	/**
	 * @return the logTextArea
	 */
	public TraceOutputUi getLogTextArea() {
		return logTextArea;
	}

	/**
	 * @param logTextArea the logTextArea to set
	 */
	public void setLogTextArea(TraceOutputUi logTextArea) {
		this.logTextArea = logTextArea;
	}

	public void setSampleApp(ACDSampleAppInterface sampleAInterface){
		acdSampleApp = sampleAInterface;
	}

	public String getParameter(ACDSampleParameters parameterName) throws Exception{
		String returnvalue = null;
		if (parameterName.equals(ACDSampleParameters.ACD))
			returnvalue = acdAddressTField.getText();
		else if (parameterName.equals(ACDSampleParameters.AGENT1))
			returnvalue = agentOneTField.getText();
		else if (parameterName.equals(ACDSampleParameters.AGENT2))
			returnvalue = agentTwoTField.getText();
		else if (parameterName.equals(ACDSampleParameters.JTAPICLASSNAME))
			returnvalue = jtapiClassNameTField.getText();
		else if (parameterName.equals(ACDSampleParameters.LOGIN))
			returnvalue = userNameTField.getText();
		else if (parameterName.equals(ACDSampleParameters.PASSWORD))
			returnvalue = new String(passwordTField.getPassword());
		else if (parameterName.equals(ACDSampleParameters.SERVICENAME))
			returnvalue = (String) servicesComboBox.getSelectedItem();
		
		return returnvalue;
	}



	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals(GET_SERVICES_COMMAND)){
			String jtapiClassName = jtapiClassNameTField.getText();
			if (jtapiClassName==null || jtapiClassName.trim().equals("")){
				JOptionPane.showMessageDialog(this, "Please enter a valid JTAPI peer name");
			}
			try {
				String []services = acdSampleApp.getServices(jtapiClassNameTField.getText());
				for (int i=0;i<services.length;i++)
					servicesComboBox.addItem(services[i]);
			} catch (JtapiPeerUnavailableException e1) {
				e1.printStackTrace();
				return;
			}
		}else if (actionCommand.equals(RUN_COMMAND)){
			final ACDSampleInitializationParameters initParameters = new ACDSampleInitializationParameters();
			
			initParameters.setAcdAddress(acdAddressTField.getText());
			initParameters.setAgentOneAddress(agentOneTField.getText());
			initParameters.setAgentOnePassword(new String(agentOnePField.getPassword()));
			initParameters.setAgentOneStation(stationOneTField.getText());
			initParameters.setAgentTwoAddress(agentTwoTField.getText());
			initParameters.setAgentTwoPassword(new String(agentTwoPField.getPassword()));
			initParameters.setAgentTwoStation(stationTwoTField.getText());
			initParameters.setJtapiClassName(jtapiClassNameTField.getText());
			initParameters.setPassword(new String(passwordTField.getPassword()));
			initParameters.setServiceName((String) servicesComboBox.getSelectedItem());
			initParameters.setUserName(userNameTField.getText());
			
//			final 
			new Thread(){
				public void run(){
					try{
						acdSampleApp.setInitializationParameters(initParameters);
						acdSampleApp.init();
						acdSampleApp.start();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}.start();
		}

	}

}

