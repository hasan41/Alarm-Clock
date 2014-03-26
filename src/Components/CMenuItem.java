package Components;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class CMenuItem extends JMenuItem{

	private static final long serialVersionUID = 1L;

	public CMenuItem(String text , String tooltip, KeyStroke acc, char Mnmonic){
		setText(text);
		setToolTipText(tooltip);
		setAccelerator(acc);
		setMnemonic(Mnmonic);
	}
}
