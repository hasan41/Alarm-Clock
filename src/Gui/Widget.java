package Gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Components.Ribbon;



public class Widget {

	public static JFrame widget;
	private JLabel title,time;
	private Timer timer;
	private WidgetPopUpMenu menu;
	public static int r = 255,g = 0,b = 0;
	public static float tIndex = 0.5f;
	
	public Widget(){
		init();
	}
	
	public void init(){
			widget = new JFrame();
			title = new JLabel("Alarm clock");
			title.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/clock_black.png"))));
			time = new JLabel("<html><font size=5 color=rgb("+r+","+"g"+","+b+")>"+Calendar.getInstance().getTime().toString()+"</font></html>");
			time.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/clock_new.png"))));
			menu = new WidgetPopUpMenu();
			JPanel mPanel = new JPanel();
			FlowLayout fl = new FlowLayout();
			fl.setHgap(30);
			fl.setVgap(50);
			mPanel.add(time);
			
			startTimer();
			addWindowListener();

			widget.setLayout(new BorderLayout());
			widget.add(title , BorderLayout.NORTH);
			widget.add(mPanel , BorderLayout.CENTER);
			widget.setUndecorated(true);
			widget.setOpacity(tIndex);
			widget.setAlwaysOnTop(true);
			widget.setSize(new Dimension(400,80));
			widget.setLocation(50,50);
			widget.setVisible(true);
	}
	
	
	public void startTimer(){
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				time.setText("<html><font size=5 color=rgb("+r+","+"g"+","+b+")>"+Calendar.getInstance().getTime().toString()+"</font></html>");
			}
		});
		
		timer.start();
	}
	
	public void addWindowListener(){
		
		widget.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				widget.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				widget.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));				
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					menu.show(widget, e.getX(), e.getY());
				}
			}

			
			
		});
		
		widget.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				widget.setLocation(e.getXOnScreen(), e.getYOnScreen());
			}
			
		});
	}
}
