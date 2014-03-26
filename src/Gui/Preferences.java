package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

import utility.MP3;
import Components.CButton;
import Components.FlowCustomLayout;
import Components.Ribbon;
import Settings.Settings;
import eu.hansolo.custom.SteelCheckBox;
import eu.hansolo.tools.ColorDef;


public class Preferences extends JDialog{

	private static final long serialVersionUID = 6533874348170575322L;
	SteelCheckBox animation;
	JRadioButton sys = new JRadioButton("System"),nimbus = new JRadioButton("Nimbus"),weblaf = new JRadioButton("Weblaf") ,  exit , widget;
	MP3 player;
	JRadioButton def , user;

	public Preferences(){
		init();
	}

	public void init(){

		setLayout(new BorderLayout());
		add(getButtonPanel() , BorderLayout.SOUTH);
		add(getMainPanel() , BorderLayout.CENTER);
		setTitle("Preferences");
		setSize(new Dimension(600,400));
		setModal(true);
		setLocationRelativeTo(AlarmClock.frame);
		setVisible(true);
	}


	public JPanel getMainPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		JTabbedPane tabs = new JTabbedPane();
		tabs.setTabPlacement(JTabbedPane.LEFT);
		tabs.addTab("General", getGeneralPanel());

		panel.setLayout(new BorderLayout());
		panel.add(tabs,BorderLayout.CENTER);
		return panel;
	}

	public JPanel getGeneralPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		JPanel mPanel = new JPanel(new BorderLayout()) , tPanel = new JPanel(new FlowLayout()) , bPanel = new JPanel(new FlowLayout());

		def = new JRadioButton("Default");
		user = new JRadioButton("User defined");	
		final JButton browse = new JButton("Browse") , play = new JButton() , stop = new JButton();
		final JLabel path = new JLabel();
		if(Settings.getInstance().isDefaultTone()){
			def.setSelected(true);
			browse.setEnabled(false);
			play.setEnabled(false);
			stop.setEnabled(false);
		}
		else{
			user.setSelected(true);
			browse.setEnabled(true);
			stop.setEnabled(false);
			play.setEnabled(false);
			if(Settings.getInstance().getUserTonePath() != null){
				path.setText(new File(Settings.getInstance().getUserTonePath()).getName());
				play.setEnabled(true);
			}
		}
		play.setToolTipText("play");
		stop.setToolTipText("stop");
		bPanel.add(play);
		bPanel.add(stop);
		play.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/play.png"))));
		stop.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/stop.png"))));
		tPanel.add(def);
		tPanel.add(user);
		tPanel.add(browse);
		tPanel.add(path);
		mPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY.brighter()), "Alarm tone"));
		def.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				browse.setEnabled(false);
				user.setSelected(false);
				play.setEnabled(false);
				stop.setEnabled(false);
				Settings.getInstance().setDefaultTone(true);
				path.setText("");
			}
		});
		user.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				browse.setEnabled(true);
				def.setSelected(false);
				play.setEnabled(true);
				
				if(Settings.getInstance().getUserTonePath() != null){
					path.setText(new File(Settings.getInstance().getUserTonePath()).getName());
				}
				
			}
		});

		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(AlarmClock.frame);
				fd.setTitle("Open mp3 file");
				fd.setFilenameFilter(new FilenameFilter(){
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".mp3");
					}
				});

				fd.setVisible(true);

				try{
					if(fd.getFiles()[0] != null){
						path.setText(fd.getFiles()[0].getName());
						Settings.getInstance().setUserTonePath(fd.getFiles()[0].getAbsolutePath());
						Settings.getInstance().setDefaultTone(false);
					}
				}catch(Exception ex){
					return;
				}

			}
		});
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(Settings.getInstance().getUserTonePath() == null || !new File(Settings.getInstance().getUserTonePath()).exists()){
					JOptionPane.showMessageDialog(AlarmClock.frame, "The file is either not supported or is not available", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				player = new MP3(Settings.getInstance().getUserTonePath());
				player.play();
				play.setEnabled(false);
				stop.setEnabled(true);
			}
		});

		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player.close();
				stop.setEnabled(false);
				play.setEnabled(true);
			}
		});

		mPanel.add(tPanel , BorderLayout.NORTH);
		mPanel.add(bPanel , BorderLayout.CENTER);

		JPanel themePanel = new JPanel(new FlowLayout());
		themePanel.add(sys);
		themePanel.add(nimbus);
		themePanel.add(weblaf);
		themePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY.brighter()), "Theme"));
		if(Settings.getInstance().getTheme().equals(Settings.SYSTEM)){
			sys.setSelected(true);
		}
		else if(Settings.getInstance().getTheme().equals(Settings.NIMBUS)){
			nimbus.setSelected(true);
		}
		else{
			weblaf.setSelected(true);
		}

		sys.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nimbus.setSelected(false);
				weblaf.setSelected(false);
				JOptionPane.showMessageDialog(AlarmClock.frame, "The effect will take place after the application is restarted.", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		nimbus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sys.setSelected(false);
				weblaf.setSelected(false);
				JOptionPane.showMessageDialog(AlarmClock.frame, "The effect will take place after the application is restarted.", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		weblaf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sys.setSelected(false);
				nimbus.setSelected(false);
				JOptionPane.showMessageDialog(AlarmClock.frame, "The effect will take place after the application is restarted.", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JPanel exAn = new JPanel(new FlowLayout());
		animation = new SteelCheckBox();
		animation.setText("on/off");
		animation.setColored(true);
		animation.setSelectedColor(ColorDef.GREEN);
		if(Settings.getInstance().isExitAnimation()){
			animation.setSelected(true);
		}
		else{
			animation.setSelected(false);
		}
		exAn.add(animation);
		exAn.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY.brighter()), "Exit Animation"));

		JPanel dPanel = new JPanel(new FlowLayout()) , sPanel = new JPanel(new BorderLayout());

		exit = new JRadioButton("Exit application");
		widget = new JRadioButton("Open widget");

		if(Settings.getInstance().getIsWidget()){
			widget.setSelected(true);
		}
		else{
			exit.setSelected(true);
		}

		dPanel.add(exit);
		dPanel.add(widget);
		dPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY.brighter()), "Default exit operation"));
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				widget.setSelected(false);
			}
		});

		widget.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exit.setSelected(false);
			}
		});

		sPanel.add(mPanel , BorderLayout.CENTER);
		sPanel.add(dPanel , BorderLayout.SOUTH);

		panel.add(sPanel , BorderLayout.CENTER);
		panel.add(themePanel , BorderLayout.NORTH);
		panel.add(exAn , BorderLayout.SOUTH);

		return panel;
	}

	public JPanel getButtonPanel(){
		JPanel panel = new JPanel(new FlowCustomLayout(FlowLayout.RIGHT));
		CButton save = new CButton("Save", "save the preferences", null, 'S'),cancel = new CButton("Cancel", "cancel and go back", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), 'C');
		panel.add(save);
		panel.add(cancel);

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(animation.isSelected()){
					Settings.getInstance().setExitAnimation(true);
				}else{
					Settings.getInstance().setExitAnimation(false);
				}

				if(sys.isSelected()){
					Settings.getInstance().setTheme(Settings.SYSTEM);
				}
				if(nimbus.isSelected()){
					Settings.getInstance().setTheme(Settings.NIMBUS);
				}
				if(weblaf.isSelected()){
					Settings.getInstance().setTheme(Settings.WEBLAF);
				}

				if(widget.isSelected()){
					Settings.getInstance().setIsWidget(true);
				}
				else{
					Settings.getInstance().setIsWidget(false);
				}

				if(def.isSelected()){
					Settings.getInstance().setDefaultTone(true);
				}
				else{
					Settings.getInstance().setDefaultTone(false);
				}

				dispose();

			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(player != null) 
					player.close();
				dispose();
			}
		});

		return panel;
	}

}
