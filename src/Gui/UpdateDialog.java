package Gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import Components.BottomPanel;
import Components.CButton;
import Components.FlowCustomLayout;

public class UpdateDialog {

	private JDialog dialog;
	private JEditorPane pane;
	private CButton update,cancel;
	
	
	public UpdateDialog(){
		init();
	}
	
	public void init(){
		dialog = new JDialog();
		dialog.setSize(new Dimension(500,400));
		dialog.setTitle("Update alarm clock");
		dialog.setModal(true);
		dialog.setLocationRelativeTo(AlarmClock.frame);
		dialog.setLayout(new BorderLayout());
		dialog.add(getMainPanel() , BorderLayout.CENTER);
		dialog.add(getBPanel() , BorderLayout.SOUTH);
		dialog.setVisible(true);
	}
	
	
	public JPanel getMainPanel(){
		JPanel panel = new JPanel();
		pane = new JEditorPane();
		pane.setBackground(dialog.getBackground());
		pane.setOpaque(false);
		pane.setEditable(false);
		
		InputStream input = BottomPanel.class.getResourceAsStream("/other/update.html");
		Scanner in = new Scanner(input);
		String temp = "";
		while(in.hasNext()){
			temp += in.nextLine();
		}
		in.close();
		pane.setContentType("text/html");
		pane.setText(temp);

		pane.addHyperlinkListener(new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		panel.add(pane);
		return panel;
	}
	
	public JPanel getBPanel(){
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowCustomLayout(FlowLayout.RIGHT));
		update = new CButton("Update now", "update the alarm clock now", null, 'U');
		cancel = new CButton("Cancel", "cancel and go back", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), 'C');
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String link = "https://github.com/musaeed/Alarm-Clock/raw/master/bin/alarm.jar";
				String fileName = System.getProperty("user.home")+"/.cache/alarmclock/alarm.jar";
				
				try{
					URL url = new URL(link);
					URLConnection c = url.openConnection();
					c.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; .NET CLR 1.0.3705; .NET CLR 1.1.4322; .NET CLR 1.2.30703)");

					InputStream input;
					input = c.getInputStream();
					byte[] buffer = new byte[4096];
					int n = -1;

					OutputStream output = new FileOutputStream(new File(fileName));
					while ((n = input.read(buffer)) != -1) {
						if (n > 0) {
							output.write(buffer, 0, n);
						}
					}
					output.close();
				} catch(Exception e){
					e.printStackTrace();
					return;
				}
				
				
				JPanel panel = new JPanel(new BorderLayout());
				JLabel label = new JLabel("This action requires sudo rights. Please enter your password:");
				JPasswordField pass = new JPasswordField(10);
				panel.add(label , BorderLayout.NORTH);
				panel.add(pass , BorderLayout.CENTER);
				String[] options = new String[]{"OK", "Cancel"};
				int option = JOptionPane.showOptionDialog(null, panel, "Enter password",JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[1]);

				if(option != 0){
					return;
				}
				
				String[] cmd = {"/bin/bash","-c","echo "+new String(pass.getPassword())+"| sudo -S mv "+System.getProperty("user.home")+"/.cache/alarmclock/alarm.jar /usr/share/alarmclock/"};
				
				try {
					
					Runtime.getRuntime().exec(cmd);
					
				} catch (IOException e) {
					JOptionPane.showMessageDialog(AlarmClock.frame, "An error occured while updating. Please try again later", "Update error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				
				dialog.dispose();
				JOptionPane.showMessageDialog(AlarmClock.frame, "JEditor successfully updated. Please restart the application.", "Update", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		panel.add(update);
		panel.add(cancel);
		return panel;
	}
}
