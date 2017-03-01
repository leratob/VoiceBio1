package route.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DestinationDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final RouteFrame parent;
	private final JTextField text;
	public DestinationDialog(RouteFrame parent, String title, String message) {
	    super(parent, title, true);
	    this.parent = parent;
	    if (parent != null) {
	      Dimension parentSize = parent.getSize(); 
	      Point p = parent.getLocation(); 
	      setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
	    }
	    JPanel messagePane = new JPanel();
	    messagePane.add(new JLabel(message));
	    text = new JTextField(10);
	    messagePane.add(text);
	    getContentPane().add(messagePane);
	    JPanel buttonPane = new JPanel();
	    JButton button = new JButton("OK"); 
	    buttonPane.add(button); 
	    button.addActionListener(this);
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    pack(); 
	    setVisible(true);
	  }
	  public void actionPerformed(ActionEvent e) {
	    setVisible(false);
	    parent.handleRouteRequest(text.getText());
	    dispose(); 
	  }
	  /*public static void main(String[] a) {
		  DestinationDialog dlg = new DestinationDialog(new JFrame(), "title", "message");
	  }*/

}
