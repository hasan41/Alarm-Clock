package Components;

import iOParsers.Writer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import Gui.Preferences;
import Gui.helpDialog;

import utility.Alarm;
import utility.AlarmUtilities;

public class CMenuBar extends JMenuBar{
	
	private static final long serialVersionUID = 4495072009090044086L;
	
	private CMenu alarm,help;
	private CMenuItem newAlarm,preferences,exit;
	private CMenuItem shortcuts,about;
	
	public CMenuBar(){
		init();
		addActions();
		addComponents();
	}
	public void init(){
		alarm = new CMenu("Alarm", 'A');
		help = new CMenu("Help", 'H');
		
		alarm.add(newAlarm = new CMenuItem("New Alarm", "add a new alarm", KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), 'N'));
		alarm.add(preferences = new CMenuItem("Preferences", "manage your preferences for the application", KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK), 'P'));
		alarm.addSeparator();
		alarm.add(exit = new CMenuItem("Exit", "exit the application", KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK), 'E'));
		
		help.add(shortcuts = new CMenuItem("Shortcuts", "shortcuts for the application", KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), 'S'));
		help.add(about = new CMenuItem("About", "about the developer", KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), 'A'));
	}
	
	public void addComponents(){
		add(alarm);
		add(help);
	}
	
	public void addActions(){
		newAlarm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Alarm.addAlarmDialog();
			}
		});
		
		preferences.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Preferences();
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Writer.writeConfig();
				AlarmUtilities.exitApplication();
			}
		});
		
		shortcuts.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new helpDialog(false);
			}
		});
		
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new helpDialog(true);
			}
		});
	}

}
