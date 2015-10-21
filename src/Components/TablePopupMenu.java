package Components;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import Gui.AlarmClock;

import utility.AlarmUtilities;

public class TablePopupMenu extends JPopupMenu{
	

	private static final long serialVersionUID = -8582866773216034702L;
	private CMenuItem edit,remove,removeAll,clearSelection,selectAll;
	
	public TablePopupMenu(){
		init();
		addComponents();
		addIcons();
		addActions();
	}
	
	public void init(){
		edit = new CMenuItem("Edit", "edit the alarm", KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK), 'E');
		remove = new CMenuItem("Remove", "remove the selected alarm", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), 'R');
		removeAll = new CMenuItem("Remove all", "remove all the alarms", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.SHIFT_DOWN_MASK), 'A');
		selectAll = new CMenuItem("Select all", "select all the alarms", KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), 'S');
		clearSelection = new CMenuItem("Clear selection", "clear the selection form the tabkle", KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), 'C');
	}
	
	public void addComponents(){
		
		add(edit);
		add(remove);
		add(removeAll);
		addSeparator();
		add(selectAll);
		add(clearSelection);
		
	}
	
	public void addIcons(){
		edit.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/edit.png"))));
		remove.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/remove_small.png"))));
		removeAll.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/removeall.png"))));
		selectAll.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/selectall.png"))));
		clearSelection.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource("images/clear.png"))));
	}
	
	public void addActions(){
		edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
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
		
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainPanel.table.getSelectedRowCount() == 0){
					JOptionPane.showMessageDialog(AlarmClock.frame, "There is no row selected to remove.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int result = JOptionPane.showConfirmDialog(AlarmClock.frame, "Are you sure you want to remove the selected alarm(s)?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
				
				if(result != JOptionPane.YES_OPTION ){
					return;
				}
				
				int[] rows = mainPanel.table.getSelectedRows();
				   for(int i=0;i<rows.length;i++){
				     ((TableModel)mainPanel.table.getModel()).removeRow(rows[i]-i);
				   }
			}
		});
		
		removeAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				int result = JOptionPane.showConfirmDialog(AlarmClock.frame, "Are you sure you want to delete all the alarms?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
				
				if(result == JOptionPane.NO_OPTION || result == JOptionPane.CANCEL_OPTION){
					return;
				}
				
				AlarmUtilities.getInstance().getList().clear();
				mainPanel.table.removeAll();
				((TableModel)mainPanel.table.getModel()).updateAlarms();
			}
		});
		
		selectAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.table.selectAll();
			}
		});
		
		clearSelection.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.table.clearSelection();
			}
		});
	}
}
