package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUI {

	private JFrame windowAutentication;
	private JFrame windowProblem;

	private JCheckBox admin_cb;
	private JCheckBox user_cb;

	public GUI() {
		//Log in window
		windowAutentication = new JFrame("Log in");
		windowAutentication.setVisible(true);
		windowAutentication.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		windowAutentication.setLocationRelativeTo(null);

		//Problem description window
		windowProblem = new JFrame("Problem description");
		windowProblem.setSize(1000, 650);
		//windowProblem.setVisible(true);
		windowProblem.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		addcontent();
		windowAutentication.pack();
	}

	private void addcontent() {
		//1. Log in window
		JPanel autenticacao_panel = new JPanel();
		autenticacao_panel.setLayout(new BorderLayout());
		windowAutentication.getContentPane().add(autenticacao_panel);

		//1.1 Username and password panel
		JPanel username_pass_panel = new JPanel();
		username_pass_panel.setLayout(new BorderLayout());
		autenticacao_panel.add(username_pass_panel, BorderLayout.NORTH);

		JPanel label_panel = new JPanel();
		label_panel.setLayout(new GridLayout(2, 1));
		username_pass_panel.add(label_panel, BorderLayout.WEST);

		JPanel textfield_panel = new JPanel();
		textfield_panel.setLayout(new GridLayout(2, 1));
		username_pass_panel.add(textfield_panel, BorderLayout.EAST);

		JLabel username_lb = new JLabel("Username: ");
		label_panel.add(username_lb);
		JTextField username_tf = new JTextField(12);
		textfield_panel.add(username_tf);
		JLabel password_lb = new JLabel("Password: ");
		label_panel.add(password_lb);
		JTextField password_tf = new JTextField(12);
		textfield_panel.add(password_tf);

		//1.2 Admin and user panel - checkbox
		JPanel admin_user_panel = new JPanel();
		autenticacao_panel.add(admin_user_panel, BorderLayout.CENTER);

		ButtonGroup button_group = new ButtonGroup();
		admin_cb = new JCheckBox("Administrador");
		button_group.add(admin_cb);
		admin_user_panel.add(admin_cb);
		user_cb = new JCheckBox("Utilizador");
		button_group.add(user_cb);
		admin_user_panel.add(user_cb);
		
		//1.3 Confirm and cancel panel
		JPanel confirm_panel = new JPanel();
		confirm_panel.setLayout(new GridLayout(1, 3));
		autenticacao_panel.add(confirm_panel, BorderLayout.SOUTH);

		JButton cancel_button = new JButton("cancel");
		confirm_panel.add(cancel_button);
		JButton confirm_button = new JButton("ok");
		confirm_panel.add(confirm_button);
		
		cancel_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "You need to log in!");
			}
		});
		
		confirm_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(user_exists() == true) {
					
				} else {
					JOptionPane.showMessageDialog(null, "Wrong password or username! Please try again!");
				}
				
			}
		});
	}
	
	// Checks if user has account
	private boolean user_exists() {
		return false;
	}
}
