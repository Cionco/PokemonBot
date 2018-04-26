package mainstuff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import serialize.Field;
import serialize.Location;

public class LinkDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	List list;
	MapGenerator root;
	Field rootField;
	Location openedLocation = null;
	JTable table = null;
	
	/**
	 * data[0] Location name
	 * data[1] Field x
	 * data[2] Field y
	 */
	int[] data = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LinkDialog dialog = new LinkDialog(null, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LinkDialog(MapGenerator root, Field rootField) {
		this.root = root;
		this.rootField = rootField;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						data = new int[3];
						data[0] = list.getSelectedIndex();
						data[1] = table.getSelectedColumn();
						data[2] = table.getSelectedRow();
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Remove Link");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						data = null;
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		
		setupWest();
		setVisible(true);
	}
	
	private void setupWest() {		
		list = new List();	
		list.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				open(list.getSelectedIndex());
			}
		});
		
		contentPanel.add(list, BorderLayout.WEST);
		
		
		for(Location l : root.getLocations()) {
			list.add(l.getName());
		}
	}
	
	private void setupCenter(int height, int width) {
		DefaultTableModel model = new DefaultTableModel(height, width) {
			@Override
			public Class<?> getColumnClass(int column) {
				return ImageIcon.class;
			}
		};
		
		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		table.setShowGrid(true);
		table.setGridColor(Color.BLACK);
		table.setRowHeight(MapGenerator.CELL_SIZE);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setCellSize();
		table.setTableHeader(null);
		table.setCellSelectionEnabled(true);
		contentPanel.add(scroll, BorderLayout.CENTER);
		
		this.revalidate();
	}
	
	private void open(int index) {
		try {
			openedLocation = root.getLocations().get(index);			
		} catch(ArrayIndexOutOfBoundsException e) {
			openedLocation = null;
		}
		
		//Remove old center
		try {
			BorderLayout layout = (BorderLayout) contentPanel.getLayout();
			contentPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
			this.revalidate();
			this.repaint();
		} catch(NullPointerException e) {		//Occurs when no center is set yet
			
		}
		if(openedLocation != null) {
			//Create new Center
			setupCenter(openedLocation.getHeight(), openedLocation.getWidth());
			
			//Recreate all fields
			for(Field f : openedLocation.getFields()) 
				if(f.getType() != null) 
					root.setTableIcon((DefaultTableModel)table.getModel(), f.getX(), f.getY(), f.getType().getIcon());
			
			if(rootField.getLink() != null && rootField.getLink().getLocName().equals(openedLocation.getName())) {
				root.setTableIcon((DefaultTableModel)table.getModel(), rootField.getLink().getX(), rootField.getLink().getY(), Tool.LINK.getIcon());
			}
		}
	}
	

	private void setCellSize() {
		Enumeration<TableColumn> cols = table.getColumnModel().getColumns();
		for(; cols.hasMoreElements(); ) {
			TableColumn tc = cols.nextElement();
			tc.setMinWidth(MapGenerator.CELL_SIZE);
			tc.setMaxWidth(MapGenerator.CELL_SIZE);
		}
	}
}
