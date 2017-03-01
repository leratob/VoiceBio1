package route.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import route.RouteData;
import route.RouteService;

import com.avaya.jtapi.tsapi.TsapiPeer;

public class RouteFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabbedPane;
	private final JTextArea area = new JTextArea();
	
	//page 1
	private JTextField fieldName;
	private JPasswordField fieldPass;
	private JTextField fieldClass;
	
	//page 2
	private JButton button;
	private JComboBox serviceName;
	private JTextField fieldVDN;
	
	private RouteService service;
	private RouteFrame me;
	public RouteFrame(){
		setTitle("Route Sample Application");
		setSize( 350, 1000 );
		setResizable(false);
		setBackground( Color.gray );
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		getContentPane().add( mainPanel );

		// Create the tab pages and message panel
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Login info", createPage1());
		tabbedPane.addTab("Route info", createPage2());
		mainPanel.add( tabbedPane);
			
		addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent evt) {
	        	if (service!=null){
	        		service.cancelRouteCallback();
	        		service.shutdown();
	        	}
	            System.exit(0);
	        }
	    });
		area.setEditable(false);
		area.setColumns(36);
        area.setRows(10);
        area.setBorder(BorderFactory.createEtchedBorder());
        
        tabbedPane.setEnabledAt(1, false);
        JScrollPane scrollPane = new JScrollPane(area);
        mainPanel.add(scrollPane);
        
        pack();
        setLocationRelativeTo(null); //center frame on screen
        me = this;
	}
	
	public JPanel createPage1() {
		JPanel panel1 = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		panel1.setLayout( gbl );
		
		//line 1
		JLabel label1 = new JLabel( "Login: " );
		panel1.add( label1 );
		GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.anchor = GridBagConstraints.LINE_END;
	    gbl.setConstraints(label1, gbc);
	    
		fieldName = new JTextField("", 30);
		panel1.add( fieldName );
		gbc = new GridBagConstraints();
	    gbc.gridx = 2;
	    gbc.gridy = 1;
	    gbl.setConstraints(fieldName, gbc);
	    
	    //line 2
		JLabel label2 = new JLabel( "Password: " );
		panel1.add( label2 );
		gbc = new GridBagConstraints();
	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    gbc.anchor = GridBagConstraints.LINE_END;
	    gbl.setConstraints(label2, gbc);
	    
		fieldPass = new JPasswordField("", 30);
		panel1.add( fieldPass );
		gbc = new GridBagConstraints();
	    gbc.gridx = 2;
	    gbc.gridy = 2;
	    gbl.setConstraints(fieldPass, gbc);
	    
	    //line 3
	    JLabel label3 = new JLabel( "JTAPI Class: " );
		panel1.add( label3 );
		gbc = new GridBagConstraints();
	    gbc.gridx = 1;
	    gbc.gridy = 3;
	    gbc.anchor = GridBagConstraints.LINE_END;
	    gbl.setConstraints(label3, gbc);
	    
	    fieldClass = new JTextField("", 30);
		panel1.add( fieldClass );
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 3;
	    gbl.setConstraints(fieldClass, gbc);
	    
	    //line 4
	    button = new JButton("Next");
		panel1.add( button );
		gbc = new GridBagConstraints();
	    gbc.gridx = 2;
	    gbc.gridy = 4;
	    gbc.anchor = GridBagConstraints.EAST;
	    gbl.setConstraints(button, gbc);
	    
	    //add listeners
	    button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setEnabledAt(1, true);
				tabbedPane.setSelectedIndex(1);
				service = new RouteService(getRouteData(), area);
				try {
					for(Object o:service.getServices()){
						boolean add = true;
						for(int i=0;i<serviceName.getItemCount();i++){
							if(serviceName.getItemAt(i).equals(o)){
								add = false;
								break;
							}
						}
						
						if (add){
							serviceName.addItem((String) o);
						}
					}
				} catch (Exception e1) {
					tabbedPane.setEnabledAt(1, false);
					tabbedPane.setSelectedIndex(0);
				}
			}
		});

		fieldClass.setText(TsapiPeer.class.getName());
		//button.setEnabled(false);
	    return panel1;
	}

	public JPanel createPage2()
	{
		JPanel panel2 = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		panel2.setLayout( gbl );
		
		//line 1
		JLabel label1 = new JLabel( "Service: " );
		panel2.add( label1 );
		GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.anchor = GridBagConstraints.LINE_END;
	    gbl.setConstraints(label1, gbc);
	    
		serviceName = new JComboBox();
		panel2.add( serviceName );
		gbc = new GridBagConstraints();
	    gbc.gridx = 2;
	    gbc.gridy = 1;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbl.setConstraints(serviceName, gbc);
	    
	    //line 2
		JLabel label2 = new JLabel( "VDN: " );
		panel2.add( label2 );
		gbc = new GridBagConstraints();
	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    gbc.anchor = GridBagConstraints.LINE_END;
	    gbl.setConstraints(label2, gbc);
	    
		fieldVDN = new JTextField("", 30);
		panel2.add( fieldVDN );
		gbc = new GridBagConstraints();
	    gbc.gridx = 2;
	    gbc.gridy = 2;
	    gbl.setConstraints(fieldVDN, gbc);
	    
	    //line 3
	    button = new JButton("Test");
		panel2.add( button );
		gbc = new GridBagConstraints();
	    gbc.gridx = 2;
	    gbc.gridy = 3;
	    gbc.anchor = GridBagConstraints.EAST;
	    gbl.setConstraints(button, gbc);
	    button.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				RouteData data = getRouteData();
				try {
					service.updateData(getRouteData());
					service.login();
					service.registerRouteCallback(me);
					service.handleRouteRequest(data.vdn);
				} catch (Exception e1) {
					//already logged
				}
			}    	
	    });
		return panel2;
	}

	//Main method to get things started
	public static void main( String args[] )
	{
		// Create an instance of the test application
		RouteFrame mainFrame = new RouteFrame();
		mainFrame.setVisible( true );
	}
	
	public RouteData getRouteData(){
		return new RouteData(
				fieldName.getText(), 
				new String(fieldPass.getPassword()),
				fieldClass.getText(), 
				(String)serviceName.getSelectedItem(), 
				fieldVDN.getText());
	}

	public void handleRouteRequest(String destination) {
		area.append("\nRouting to destination '"+ destination +"'\n\n" );
		service.handleRouteRequest(destination);
		
	}

	public void disableButton() {
		button.setEnabled(false);
	}
	
	
}
