package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import Components.CButton;
import Components.CMenuItem;
import Components.FlowCustomLayout;

public class WidgetPopUpMenu extends JPopupMenu{

	private static final long serialVersionUID = -2862779787906616477L;
	private CMenuItem timeColor,setSize,showMainFrame,transparencySettings,minimize,exit;

	public WidgetPopUpMenu(){
		init();
		addComponents();
		addActions();
	}
	
	public void init(){
		timeColor = new CMenuItem("Time color", "select the time color", null, 'T');
		setSize = new CMenuItem("Set size", "set the size of the widget", null, 'E');
		showMainFrame = new CMenuItem("Show main frame", "show the main frame", null, 'S');
		transparencySettings = new CMenuItem("Transparency setting", "set the transparency settings", null, 'R');
		minimize = new CMenuItem("Minimze", "minimize the widget", null, 'M');
		exit = new CMenuItem("Exit", "exit the application", null, 'E');
	}
	
	public void addComponents(){
		add(minimize);
		add(setSize);
		add(timeColor);
		add(transparencySettings);
		add(showMainFrame);
		add(exit);
	}
	
	public void addActions(){
		minimize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Widget.widget.setExtendedState(JFrame.ICONIFIED);
			}
		});
		
		setSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JTextField height = new JTextField();
				JTextField width = new JTextField();
				Object[] message = {
				    "Height:", height,
				    "Width:", width
				};

				height.setText((int)Widget.widget.getSize().getHeight()+"");
				width.setText((int)Widget.widget.getSize().getWidth()+"");
				
				int option = JOptionPane.showConfirmDialog(null, message, "Enter size", JOptionPane.OK_CANCEL_OPTION);
				
				if (option == JOptionPane.OK_OPTION) {
					try{
					Widget.widget.setSize(new Dimension(Integer.parseInt(width.getText()) , Integer.parseInt(height.getText())));
					} catch(Exception e){
						JOptionPane.showMessageDialog(null, "Please enter a valid size.", "Error", JOptionPane.ERROR_MESSAGE);
						setSize.doClick();
					}
				}
			}
		});
		
		showMainFrame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Widget.widget.setVisible(false);
				AlarmClock.frame.setVisible(true);
			}
		});
		
		timeColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(null, "Select time color", new Color(Widget.r,Widget.g,Widget.b));
				
				if(c == null){
					return;
				}
				
				Widget.r = c.getRed();
				Widget.g = c.getGreen();
				Widget.b = c.getBlue();
				
			}
		});
		
		transparencySettings.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showTransparencyDialog();
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, "Alarm clock cannot show any Alarms once the application is exited. Are you sure you want to exit?", "Confirm", JOptionPane.YES_NO_OPTION);
				
				if(result == JOptionPane.NO_OPTION){
					return;
				}
				
				System.exit(0);
			}
		});
	}
	
	public void showTransparencyDialog(){
		final JDialog dialog = new JDialog();
		JPanel mPanel = new JPanel(new FlowLayout()), bPanel = new JPanel(new FlowCustomLayout(FlowLayout.RIGHT));
		CButton set = new CButton("Set", "set the settings", null, 'S'),cancel = new CButton("Cancel", "cancel and go back", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), 'C');
		final JSlider slider = new JSlider(0, 100, (int)(Widget.tIndex * 100f));
		JLabel label = new JLabel("Intensity:");
		
		mPanel.add(label);
		mPanel.add(slider);
		
		bPanel.add(set);
		bPanel.add(cancel);
		
		set.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Widget.tIndex =  ((float)slider.getValue()/100f);
				Widget.widget.setOpacity(Widget.tIndex);
				dialog.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		dialog.setLayout(new BorderLayout());
		dialog.add(mPanel , BorderLayout.CENTER);
		dialog.add(bPanel , BorderLayout.SOUTH);
		dialog.setTitle("Transparency Settings");
		dialog.setSize(new Dimension(400,150));
		dialog.setModal(true);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
