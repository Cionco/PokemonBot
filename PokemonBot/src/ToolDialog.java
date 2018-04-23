import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolDialog extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	public Waytype data = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ToolDialog dialog = new ToolDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ToolDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(3, 4));
		
		for(Waytype wt : Waytype.values()) {
			JButton newButton = new JButton(wt.toString());
			newButton.addActionListener(this);
			contentPanel.add(newButton);
		}
		
		this.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		data = Waytype.valueOf(((JButton)e.getSource()).getText());
		this.dispose();
	}
	
}
