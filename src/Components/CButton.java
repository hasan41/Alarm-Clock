package Components;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class CButton extends JButton{
	
	private static final long serialVersionUID = 1L;

	public CButton(String text , String tooltip , KeyStroke stroke , char Mnmonic){
		setText(text);
		setToolTipText(tooltip);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(stroke, 0);
		getActionMap().put(0, new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				doClick();
			}
		});
		
		setMnemonic(Mnmonic);
	}

}
