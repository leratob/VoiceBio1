package acd.ui;

import java.awt.Color;

import javax.swing.JTextArea;

public class MyTextArea extends JTextArea implements TraceOutputUi 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7975518618013507643L;
	public MyTextArea(int rows,int cols){
		super(rows, cols);
		setEditable(false);
	}

    public MyTextArea() {
        super();
        currentLength = 0;
        setEditable(false);
        setBackground(Color.white);
    }
        
    /* (non-Javadoc)
	 * @see com.avaya.aes.samples.acd.ui.TraceOutputUi#append(boolean, java.lang.String)
	 */
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
    
    /* (non-Javadoc)
	 * @see com.avaya.aes.samples.acd.ui.TraceOutputUi#append(java.lang.String)
	 */
    public synchronized void append (String str) {
        
        this.append(true, str);
    }
    
    /* (non-Javadoc)
	 * @see com.avaya.aes.samples.acd.ui.TraceOutputUi#setState(boolean)
	 */
    public void setState (boolean state) {

        if (state) {
            tracing = true;
        } else {
            tracing = false;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.avaya.aes.samples.acd.ui.TraceOutputUi#clear()
	 */
    public void clear () {

        this.setText("");
    }

    int          currentLength;
    boolean      tracing = false;
}