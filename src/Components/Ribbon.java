package Components;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import utility.Alarm;
import utility.AlarmUtilities;
import Gui.AlarmClock;
import Gui.helpDialog;

public class Ribbon extends JPanel{

	private static final long serialVersionUID = 6374848825254725439L;
	
	private RibbonButton add,remove,edit,help;
	
	public Ribbon(){
		init();
		addComponents();
		addIcons();
		addAccelarators();
		addActions();
	}
	
	public void init(){
		add = new RibbonButton("New Alarm", "add a new alarm");
		remove = new RibbonButton("Remove", "remove a selected alarm");
		edit = new RibbonButton("Edit", "edit the selected alarm");
		help = new RibbonButton("Help", "view the help dialog");
		
	}
	
	public void addComponents(){
		setLayout(new FlowCustomLayout(FlowLayout.LEFT));
		
		add(add);
		add(new CSeparator());
		add(remove);
		add(new CSeparator());
		add(edit);
		add(new CSeparator());
		add(help);
	}
	
	public void addAccelarators(){
		edit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		remove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
	}
	
	public void addIcons(){
		add.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/add.png"))));
		remove.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/remove.png"))));
		edit.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/settings.png"))));
		help.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/help.png"))));
	}
	
	public void addActions(){
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Alarm.addAlarmDialog();
			}
		});
		
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(mainPanel.table.getSelectedRowCount() == 0){
					JOptionPane.showMessageDialog(AlarmClock.frame, "There is no row selected to remove.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(mainPanel.table.getSelectedRowCount() > 1){
					JOptionPane.showMessageDialog(AlarmClock.frame, "Please select a single row to remove.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int result = JOptionPane.showConfirmDialog(AlarmClock.frame, "Are you sure you want to remove the selected alarm?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
				
				if(result != JOptionPane.YES_OPTION ){
					return;
				}
				
				int[] rows = mainPanel.table.getSelectedRows();
				
					for(Integer i : rows){
						AlarmUtilities.getInstance().getList().remove(AlarmUtilities.getInstance().getList().get(i));
					}
				
				   for(int i=0;i<rows.length;i++){
				     ((TableModel)mainPanel.table.getModel()).removeRow(rows[i]-i);
				   }
			}
		});
		
		edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainPanel.table.getSelectedRowCount() == 0){
					JOptionPane.showMessageDialog(AlarmClock.frame, "There is no row selected to edit.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(mainPanel.table.getSelectedRowCount() > 1){
					JOptionPane.showMessageDialog(AlarmClock.frame, "Please select a single row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				AlarmUtilities.showEditDialog(AlarmUtilities.getInstance().getList().get(mainPanel.table.getSelectedRow()));
				
			}
		});
		
		help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new helpDialog(false);
			}
		});
	}

}
