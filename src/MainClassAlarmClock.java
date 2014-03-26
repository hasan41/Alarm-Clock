import iOParsers.Reader;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Gui.AlarmClock;
import Settings.Settings;

import com.alee.laf.WebLookAndFeel;


public class MainClassAlarmClock {
	public static void main(String args[]){
		
		
		try {
			Reader.readConfig();
		}
		catch(Exception e){
			e.printStackTrace();
			Settings.getInstance().setDefaultTone(true);
			Settings.getInstance().setExitAnimation(false);
			Settings.getInstance().setTheme(Settings.SYSTEM);
			Settings.getInstance().setUserTonePath(null);
			
		}
		
		try {
			if(Settings.getInstance().getTheme().equals(Settings.SYSTEM)){
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			else if(Settings.getInstance().getTheme().equals(Settings.NIMBUS)){
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			}
			else{
				WebLookAndFeel.install();
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		new AlarmClock();
	}
}
