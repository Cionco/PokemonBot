package mainstuff;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import serialize.Field;
import serialize.LoadListener;
import serialize.Location;
import serialize.LocationFieldDao;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class MapGenerator extends JFrame implements LoadListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7504674375749508612L;
	private JPanel contentPane;
	private JTextField txt_x;
	private JTextField txt_y;
	private JTextField txt_type;
	private JTextField txt_name;
	private JTextArea txa_desc;
	private List list;
	private JTable table;
	private ArrayList<Location> locations = new ArrayList<Location>();
	private Location openedLocation;
	
	public static final int CELL_SIZE = 20;
	
	private Tool current = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapGenerator frame = new MapGenerator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MapGenerator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		setupWest();
		
		setupNorth();
		
		//setupCenter(20, 20);
		
		setupEast();
	}

	private void setupWest() {
		JPanel westPane = new JPanel();
		westPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		westPane.setLayout(new GridBagLayout());
		
		list = new List();	
		list.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				open(list.getSelectedIndex());
			}
		});
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.NORTH;
		
		westPane.add(list, c);
		
		
		JButton btn_plus = new JButton("+");
		btn_plus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewLocation();
			}
		});
		btn_plus.setForeground(Color.GREEN);
		
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.SOUTH;
		
		westPane.add(btn_plus, c);
		
		
		JButton btn_del = new JButton("x");
		btn_del.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete the location: " + list.getSelectedItem(), "Delete Location?", JOptionPane.OK_CANCEL_OPTION);
				if(opt == JOptionPane.CANCEL_OPTION) return;
				
				Location selectedLocation = locations.get(list.getSelectedIndex());
				locations.remove(list.getSelectedIndex());
				list.remove(list.getSelectedIndex());
				if(selectedLocation == openedLocation) {
					list.select(0);
					open(list.getSelectedIndex());
				}
			}
		});
		btn_del.setForeground(Color.RED);
		
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.SOUTH;
		
		westPane.add(btn_del, c);
		
		contentPane.add(westPane, BorderLayout.WEST);
	}
	
	private void setupNorth() {
		JPanel northPane = new JPanel();
		northPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		northPane.setLayout(new GridLayout(1, 5));
		
		contentPane.add(northPane, BorderLayout.NORTH);
		
		JButton btn_new = new JButton("new");
		btn_new.setBounds(33, 124, 0, 0);
		btn_new.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewLocation();
			}
		});
		northPane.add(btn_new);
		
		JButton btn_save = new JButton("save");
		btn_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		northPane.add(btn_save);
		
		JButton btn_open = new JButton("open");
		btn_open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocationFieldDao.load();
			}
		});
		northPane.add(btn_open);
		
		JPanel x_coordPane = new JPanel();
		x_coordPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		x_coordPane.setLayout(new GridLayout(1, 2));
		
		x_coordPane.add(new JLabel("x:", SwingConstants.RIGHT));
		txt_x = new JTextField();
		x_coordPane.add(txt_x);
		txt_x.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(openedLocation == null) {
					JOptionPane.showConfirmDialog(null, "Select Location!", "No location selected", JOptionPane.DEFAULT_OPTION);
					return;
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				if(Integer.parseInt(txt_x.getText()) > table.getColumnCount()) 
					for(int i = table.getColumnCount(); i < Integer.parseInt(txt_x.getText()); i++)
						model.addColumn("");
				else
					for(int i = table.getColumnCount() - 1; i >= Integer.parseInt(txt_x.getText()); i--)
						model.setColumnCount(table.getColumnCount() - 1);
				
				setCellSize();
				loc().updateSize(table);
			}
		});
		
		JPanel y_coordPane = new JPanel();
		y_coordPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		y_coordPane.setLayout(new GridLayout(1, 2));
		
		y_coordPane.add(new JLabel("y:", SwingConstants.RIGHT));
		txt_y = new JTextField();
		y_coordPane.add(txt_y);
		txt_y.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(openedLocation == null) {
					JOptionPane.showConfirmDialog(null, "Select Location!", "No location selected", JOptionPane.DEFAULT_OPTION);
					return;
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				if(Integer.parseInt(txt_y.getText()) > table.getRowCount()) 
					for(int i = table.getRowCount(); i < Integer.parseInt(txt_y.getText()); i++) 
						model.addRow(new String[table.getColumnCount()]);					
				else 
					for(int i = table.getRowCount() - 1; i >= Integer.parseInt(txt_y.getText()); i--) 
						model.removeRow(i);
				
				loc().updateSize(table);
			}
		});
		
		northPane.add(x_coordPane);
		northPane.add(y_coordPane);
	}

	private void setupEast() {
		JPanel eastPane = new JPanel();
		eastPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		eastPane.setLayout(new GridLayout(2, 1));
		
		JPanel descPaneEast = new JPanel();
		descPaneEast.setBorder(new EmptyBorder(0, 0, 0, 0));
		descPaneEast.setLayout(new GridLayout(2, 1));
		
		descPaneEast.add(new JLabel("description"));
		txa_desc = new JTextArea();
		txa_desc.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {}

			@Override
			public void focusLost(FocusEvent e) {
				try {
					openedLocation.setDescription(txa_desc.getText());
				} catch (NullPointerException ex) {
					txa_desc.setText("");
				}
			}
			
		});
		descPaneEast.add(txa_desc);
		
		JPanel toolPaneEast = new JPanel();
		toolPaneEast.setBorder(new EmptyBorder(0, 0, 0, 0));
		toolPaneEast.setLayout(new GridLayout(2, 1));
		
		toolPaneEast.add(new JLabel("tool"));
	
		JButton btn_selectTool = new JButton("Choose");
		btn_selectTool.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolDialog dlg = new ToolDialog();
				
				dlg.addWindowListener(new WindowAdapter() {

					@Override
					public void windowDeactivated(WindowEvent e) {
						current = dlg.data;
						btn_selectTool.setText("");
						adaptButtonIconSize(btn_selectTool, current.getIcon());
						super.windowDeactivated(e);
					}
				});
			}
		});
		
		btn_selectTool.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				adaptButtonIconSize(btn_selectTool);				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
			
		});
		toolPaneEast.add(btn_selectTool);
		
		eastPane.add(descPaneEast);
		eastPane.add(toolPaneEast);
		
		contentPane.add(eastPane, BorderLayout.EAST);
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
		table.setRowHeight(CELL_SIZE);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setCellSize();
		table.setTableHeader(null);
		table.setCellSelectionEnabled(true);
		contentPane.add(scroll, BorderLayout.CENTER);
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(current != Tool.LINK){
					setTableIcon((DefaultTableModel)table.getModel(), table.getSelectedColumn(), table.getSelectedRow(), current.getIcon());
					openedLocation.setFieldType(table.getSelectedColumn(), table.getSelectedRow(), current);
				} else {
					LinkDialog dlg = new LinkDialog(MapGenerator.this, field());
					
					dlg.addWindowListener(new WindowAdapter() {
						public void windowDeactivated(WindowEvent e) {
							int[] data = dlg.data;
					
							if(data != null) {
								int linkLocIndex = data[0];
								int linkFieldX = data[1];
								int linkFieldY = data[2];
							
								field().addLink(locations.get(linkLocIndex).getName(), linkFieldX, linkFieldY);
								setTableIcon((DefaultTableModel)table.getModel(), table.getSelectedColumn(), table.getSelectedRow(), Tool.LINK.getIcon(field().getType()));
								MapGenerator.this.revalidate();
							} else {
								field().removeLink();
								setTableIcon((DefaultTableModel)table.getModel(), table.getSelectedColumn(), table.getSelectedRow(), field().getType().getIcon());
							}
						}
					});
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(current != Tool.LINK) {
					if(current == null) {

						JOptionPane.showConfirmDialog(null, "Select Tool First!", "No tool selected", JOptionPane.DEFAULT_OPTION);
						return;
					}
					ImageIcon image = current.getIcon();
					for(int row : table.getSelectedRows())
						for(int column : table.getSelectedColumns()) {
							setTableIcon((DefaultTableModel)table.getModel(), column, row, image);
							openedLocation.setFieldType(column, row, current);
						}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
		});
		this.revalidate();
	}

	private void setCellSize() {
		Enumeration<TableColumn> cols = table.getColumnModel().getColumns();
		for(; cols.hasMoreElements(); ) {
			TableColumn tc = cols.nextElement();
			tc.setMinWidth(CELL_SIZE);
			tc.setMaxWidth(CELL_SIZE);
		}
	}

	private void addNewLocation() {
		String name = JOptionPane.showInputDialog("Location Name: ");
		list.add(name);
		list.select(list.getItemCount() - 1);
		locations.add(new Location(name));
		open(list.getItemCount() - 1);
	}
	
	private void open(int index) {
		try {
			openedLocation = locations.get(index);			
		} catch(ArrayIndexOutOfBoundsException e) {
			openedLocation = null;
		}
		
		//Remove old center
		try {
			BorderLayout layout = (BorderLayout) contentPane.getLayout();
			contentPane.remove(layout.getLayoutComponent(BorderLayout.CENTER));
			this.revalidate();
			this.repaint();
		} catch(NullPointerException e) {		//Occurs when no center is set yet
			
		}
		if(openedLocation != null) {
			//Create new Center
			setupCenter(openedLocation.getHeight(), openedLocation.getWidth());
			
			txt_x.setText(Integer.toString(openedLocation.getWidth()));
			txt_y.setText(Integer.toString(openedLocation.getHeight()));
			
			//Recreate all fields
			for(Field f : openedLocation.getFields()) 
				if(f.getType() != null) 
					setTableIcon((DefaultTableModel)table.getModel(), f.getX(), f.getY(), f.getType().getIcon());
			
			txa_desc.setText(openedLocation.getDescription());
		}
	}

	private void save() {
		try {
			LocationFieldDao.saveAll(locations);
		} catch (IOException e) {
			int choice = JOptionPane.showConfirmDialog(null, "An error has occured while saving! Try again?\n\n\n" + e.getMessage());
			
			if(choice == JOptionPane.OK_OPTION) save();
		}
	}
	
	private void adaptButtonIconSize(JButton button, ImageIcon image) {
		if(image == null) {
			button.setIcon(null);
			button.setText(current.toString());
			return;
		}
		
		Dimension size = button.getSize();
		Insets insets = button.getInsets();
		size.width -= insets.left + insets.right;
		size.height -= insets.top + insets.bottom;
		if (size.width > size.height) {
		    size.width = -1;
		} else {
		    size.height = -1;
		}
		Image scaled = image.getImage().getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(scaled));
	}
	
	private void adaptButtonIconSize(JButton button) {
		try {
			adaptButtonIconSize(button, current.getIcon());
		} catch(NullPointerException e) {
			
		}
	}

	public void setTableIcon(DefaultTableModel model, int x, int y, ImageIcon image) {	
		if(image == null) {
			model.setValueAt(null, y, x);
			return;
		}
		
		Dimension size = new Dimension(CELL_SIZE, CELL_SIZE);
		if (size.width > size.height) {
		    size.width = -1;
		} else {
		    size.height = -1;
		}
		Image scaled = image.getImage().getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
		model.setValueAt(new ImageIcon(scaled), y, x);
	}

	public Location loc() {
		//return locations.get(list.getSelectedIndex());
		return openedLocation;
	}
	
	private Field field() {
		return openedLocation.getField(table.getSelectedColumn(), table.getSelectedRow());
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	@Override
	public void loadingDone(ArrayList<Location> locations) {
		this.locations = locations;
		for(int i = 0; i < locations.size(); i++) 
			list.add(locations.get(i).getName());
	}
}