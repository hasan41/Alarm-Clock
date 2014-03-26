package utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

import org.freixas.jcalendar.JCalendar;

import Components.CButton;
import Components.FlowCustomLayout;
import Gui.AlarmClock;



public class Alarm {

	private Date time;
	private String alertMessage;
	private String priority;

	public static String LOW = "Low";
	public static String MED = "Medium";
	public static String HIGH = "High";

	public Alarm(Date time , String alertMessage, String priority){
		this.time = time;
		this.alertMessage = alertMessage;
		this.priority = priority;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public static void addAlarmDialog(){
		
		final JDialog dialog = new JDialog();
		CButton add = new CButton("Set", "set the alarm", null, 'S'),cancel = new CButton("Cancel", "cancel and go back", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), 'C');
		JPanel bPanel = new JPanel(new FlowCustomLayout(FlowLayout.RIGHT));
		JPanel mainPanel = new JPanel(new GridLayout(1,2)) , left = new JPanel(new FlowCustomLayout(FlowLayout.LEFT)), right = new JPanel();
		final JEditorPane message = new JEditorPane();
		final JRadioButton low = new JRadioButton("Low");
		final JRadioButton mid = new JRadioButton("Mid");
		final JRadioButton high = new JRadioButton("High");
		final JLabel priority = new JLabel();
		JScrollPane scroll = new JScrollPane(message);
		final JCalendar calender = new JCalendar(JCalendar.DISPLAY_DATE | JCalendar.DISPLAY_TIME,true);
		
		message.setSize(20, 10);
		mid.setSelected(true);
	
		
		right.setLayout(new BorderLayout());
		JPanel mPanel = new JPanel(new BorderLayout());
		mPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY.brighter()), "Message"));
		mPanel.add(scroll , BorderLayout.CENTER);
		right.add(mPanel , BorderLayout.CENTER);
		
		JPanel pPanel = new JPanel(new FlowLayout());
		pPanel.add(new JLabel("Priority:"));
		pPanel.add(low);
		pPanel.add(mid);
		pPanel.add(high);
		
		right.add(pPanel , BorderLayout.SOUTH);
		
		left.add(calender);
		
		mainPanel.add(left);
		mainPanel.add(right);
		
		
		bPanel.add(add);
		bPanel.add(cancel);
		
		low.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mid.setSelected(false);
				high.setSelected(false);
				priority.setText(LOW);
			}
		});
		
		mid.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				low.setSelected(false);
				high.setSelected(false);
				priority.setText(MED);
			}
		});
		
		high.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mid.setSelected(false);
				low.setSelected(false);
				priority.setText(HIGH);
			}
		});
		
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				AlarmUtilities.getInstance().addToList(new Alarm(calender.getDate(), message.getText(), high.isSelected() ? Alarm.HIGH : mid.isSelected() ? Alarm.MED : Alarm.LOW));
				((Components.TableModel)Components.mainPanel.table.getModel()).updateAlarms();
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
		dialog.add(bPanel , BorderLayout.SOUTH);
		dialog.add(mainPanel , BorderLayout.CENTER);
		dialog.setTitle("New Alarm");
		dialog.setModal(true);
		dialog.setSize(new Dimension(580,350));
		dialog.setLocationRelativeTo(AlarmClock.frame);
		dialog.setVisible(true);
	}
}
