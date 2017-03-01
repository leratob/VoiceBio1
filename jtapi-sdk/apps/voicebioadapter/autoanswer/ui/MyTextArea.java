package autoanswer.ui;

import java.awt.Color;
import java.awt.TextArea;

public class MyTextArea extends TextArea 
{
	private static final long serialVersionUID = 1L;
	public MyTextArea() {
        super();
        currentLength = 0;
        setEditable(false);
        setBackground(Color.white);
    }
        
    public synchronized void append(boolean always, String str) {
        
        if ( tracing || always ) {
            currentLength += str.length();
            if (currentLength > 28000) {
                replaceRange("", 0, 14000);
                currentLength = getText().length();
            }
            super.append(str);
        }
    }
    
    public synchronized void append (String str) {
        
        this.append(true, str);
    }
    
    public void setState (boolean state) {

        if (state) {
            tracing = true;
        } else {
            tracing = false;
        }
    }
    
    public void clear () {

        this.setText("");
    }

    int          currentLength;
    boolean      tracing = false;
}