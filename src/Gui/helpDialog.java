package Gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import Components.BottomPanel;
import Components.CButton;
import Components.FlowCustomLayout;

public class helpDialog extends JDialog{

	private static final long serialVersionUID = -1970253555979779670L;
	private JTable table;
	private JScrollPane pane;
	private JEditorPane editor;
	private JTabbedPane tabs;
	private CButton sendEmail , close;

	public helpDialog(boolean isAbout){
		init(isAbout);
		addComponents();
	}

	public void init(boolean isAbout){
		String columnNames[] = {"Key" , "Function"};
		Object [][] data = {{"Ctrl + N","New alarm"},
				{"Ctrl + E","Edit the selected alarm"},
				{"Ctrl + A" , "Select all alarms"},
				{"Ctrl + Z","Clear Selection"},
				{"Delete","Delete the selected alarm"},
				{"Ctrl + P","Preferences"},
				{"Alt + F4" , "Exit the application"}};
		table = new JTable(data, columnNames){

			private static final long serialVersionUID = -8625726229160953589L;

			public boolean isCellEditable(int data , int col){
				return false;
			}
		};
		pane = new JScrollPane(table);
		tabs = new JTabbedPane();
		editor = new JEditorPane();

		sendEmail = new CButton("Send email", "send an email to the developer", null, 'S');
		close = new CButton("Close", "close the dialog", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), 'C');

		tabs.addTab("Shortcuts", getShortcutsPanel());
		tabs.addTab("About", getAboutPanel());

		if(isAbout){
			tabs.setSelectedIndex(1);
		}
		else{
			tabs.setSelectedIndex(0);
		}


	}

	public JPanel getShortcutsPanel(){

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		editor.setBackground(getBackground());
		editor.setOpaque(false);
		editor.setEditable(false);

		InputStream input = BottomPanel.class.getResourceAsStream("/other/developer.html");
		Scanner in = new Scanner(input);
		String temp = "";
		while(in.hasNext()){
			temp += in.nextLine();
		}
		in.close();
		editor.setContentType("text/html");
		editor.setText(temp);

		editor.addHyperlinkListener(new HyperlinkListener() {

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

		panel.add(pane , BorderLayout.CENTER);

		return panel;
	}

	public JPanel getAboutPanel(){

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(editor , BorderLayout.CENTER);

		return panel;
	}

	public JPanel getBottomPanel(){

		JPanel panel = new JPanel();
		panel.setLayout(new FlowCustomLayout(FlowCustomLayout.RIGHT));
		sendEmail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URI("mailto:muhammad.omar555@gmail.com"));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});

		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		panel.add(sendEmail);
		panel.add(close);


		return panel;
	}

	public void addComponents(){
		setSize(new Dimension(500,500));
		setLayout(new BorderLayout());
		setModal(true);

		add(tabs , BorderLayout.CENTER);
		add(getBottomPanel() , BorderLayout.SOUTH);

		setTitle("Help");
		setLocationRelativeTo(AlarmClock.frame);
		setVisible(true);
	}

}
