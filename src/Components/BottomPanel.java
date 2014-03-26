package Components;

import iOParsers.Writer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import utility.AlarmUtilities;
import Gui.AlarmClock;

public class BottomPanel extends JPanel{

	private static final long serialVersionUID = 4573410495714065035L;
	private CButton minimize, exit;
	private JLabel timeLabel;
	private Timer timer;
	
	public BottomPanel(){
		init();
		addComponents();
		addActions();
	}
	
	public void init(){
		
		timeLabel = new JLabel("<html><font size=4.5>"+Calendar.getInstance().getTime()+"</font></html>");
		timeLabel.setToolTipText("current time");
		minimize = new CButton("Minimize", "minimize the application", null, 'M');
		exit = new CButton("Exit", "exit the application", null, 'E');
	}
	
	public void addComponents(){
		setLayout(new BorderLayout());
		
		JPanel left = new JPanel(),right = new JPanel();
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.LEFT);
		f.setVgap(12);
		left.setLayout(f);
		right.setLayout(new FlowCustomLayout(FlowLayout.RIGHT));
		
		
		right.add(minimize);
		right.add(exit);
		
		left.add(timeLabel);
		
		add(left,BorderLayout.WEST);
		add(right , BorderLayout.EAST);
	}
	
	public void addActions(){
		minimize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AlarmClock.frame.setExtendedState(JFrame.ICONIFIED);
			}
			
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Writer.writeConfig();
				AlarmUtilities.exitApplication();
			}
		});
		
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				timeLabel.setText("<html><font size=4.5>"+Calendar.getInstance().getTime() + "</font></html>");
			}
		});
		
		timer.start();
	}
	
}
