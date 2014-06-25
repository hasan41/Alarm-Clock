package Gui;

import iOParsers.Writer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import utility.AlarmUtilities;
import Components.BottomPanel;
import Components.CMenuBar;
import Components.Ribbon;
import Components.TableModel;
import Components.mainPanel;

public class AlarmClock {

	public static JFrame frame;
	public static double Version = 1.1;

	public AlarmClock(){
		initGui();
	}

	public void initGui(){
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(new CMenuBar());
		frame.setTitle("Alarm clock");
		frame.setLayout(new BorderLayout());
		frame.add(new Ribbon() , BorderLayout.NORTH);
		frame.add(new mainPanel() , BorderLayout.CENTER);
		frame.add(new BottomPanel() , BorderLayout.SOUTH);
		frame.setSize(new Dimension(620,510));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				Writer.writeConfig();
				AlarmUtilities.exitApplication();
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				frame.update(frame.getGraphics());
			}
			
			

		});
		
		((TableModel)Components.mainPanel.table.getModel()).updateAlarms();
		
		frame.setVisible(true);
	}
}
