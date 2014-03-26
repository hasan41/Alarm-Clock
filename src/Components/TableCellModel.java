package Components;

import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellModel extends DefaultTableCellRenderer{

	private static final long serialVersionUID = -8169450133615777227L;
	private String resource;
	
	public TableCellModel(String resource){
		super();
		this.resource = resource;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ribbon.class.getClassLoader().getResource(resource)));
		setText((String)value);
		setIcon(icon);
		return this;
	}
}
