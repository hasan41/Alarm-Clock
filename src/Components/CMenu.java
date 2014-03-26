package Components;

import javax.swing.JMenu;

public class CMenu extends JMenu{

	private static final long serialVersionUID = 4495072009090044086L;

	public CMenu(String text , char Mnmonic){
		setText(text);
		setMnemonic(Mnmonic);
	}
}
