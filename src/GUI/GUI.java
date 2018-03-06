package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import Email.*;
import FrequentQuestions.*;
import Users.*;

public class GUI {

	private JFrame windowAutentication;
	private JFrame windowProblem;
	private JFrame windowHelp;

	private JCheckBox admin_cb;
	private JCheckBox user_cb;

	private JTextField username_tf;
	private JPasswordField password_pf;

	@SuppressWarnings("unused")
	private Database database;
	private ArrayList<General_user> admins;
	private ArrayList<General_user> users;

	public GUI() {
		Database database = new Database();

		admins = database.getAdmins();
		users = database.getUsers();

		login_window();
		problem_description_window();
		help_window();
		
		windowAutentication.pack();
	}

	private void login_window() {
		//Log in window
		windowAutentication = new JFrame("Log in");
		windowAutentication.setVisible(true);
		windowAutentication.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		windowAutentication.setLocationRelativeTo(null);

		//1. Log in window panel
		JPanel login_panel = new JPanel();
		login_panel.setLayout(new BorderLayout());
		windowAutentication.add(login_panel);

		//1.1 Username and password panel
		JPanel username_pass_panel = new JPanel();
		username_pass_panel.setLayout(new BorderLayout());
		login_panel.add(username_pass_panel, BorderLayout.NORTH);

		JPanel label_panel = new JPanel();
		label_panel.setLayout(new GridLayout(2, 1));
		username_pass_panel.add(label_panel, BorderLayout.WEST);

		JPanel textfield_panel = new JPanel();
		textfield_panel.setLayout(new GridLayout(2, 1));
		username_pass_panel.add(textfield_panel, BorderLayout.EAST);

		JLabel username_lb = new JLabel("Username: ");
		label_panel.add(username_lb);
		username_tf = new JTextField(12);
		textfield_panel.add(username_tf);
		JLabel password_lb = new JLabel("Password: ");
		label_panel.add(password_lb);
		password_pf = new JPasswordField(12);
		textfield_panel.add(password_pf);

		//1.2 Admin and user panel - checkbox
		JPanel admin_user_panel = new JPanel();
		login_panel.add(admin_user_panel, BorderLayout.CENTER);

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
		login_panel.add(confirm_panel, BorderLayout.SOUTH);

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
					JOptionPane.showMessageDialog(null, "Succeeded!");
					windowProblem.setVisible(true);
				} else {
					check_wrong_permissions();
				}
			}
		});
	}

	// Checks if user has account
	@SuppressWarnings("deprecation")
	private boolean user_exists() {
		if(admin_cb.isSelected()) {
			for (General_user  admin: admins) {														
				if(admin.getUsername().equals(username_tf.getText()) && admin.getPassword().equals(password_pf.getText())) 
					return true;
			}
		} else {
			if(user_cb.isSelected()) {
				for (General_user  user: users) {													
					if(user.getUsername().equals(username_tf.getText()) && user.getPassword().equals(password_pf.getText())) 
						return true;
				}
			}
		}
		return false;
	}

	//Verifies if permissions are selected correctly
	@SuppressWarnings("deprecation")
	private void check_wrong_permissions() {
		if(user_cb.isSelected()) {
			for (General_user  admin: admins) {													
				if(admin.getUsername().equals(username_tf.getText()) && admin.getPassword().equals(password_pf.getText())) 
					JOptionPane.showMessageDialog(null, "Wrong permissions! You're an administrator!");
			}

			if(admin_cb.isSelected()) {
				for (General_user  user: users) {													
					if(user.getUsername().equals(username_tf.getText()) && user.getPassword().equals(password_pf.getText())) 
						JOptionPane.showMessageDialog(null, "Wrong permissions! You are a user!");
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Wrong password or username! \n Please try again!");
		}
	}

	private void problem_description_window() {
		//Problem description window
		windowProblem = new JFrame("Problem description");
		windowProblem.setSize(600, 350);
		windowProblem.setLocationRelativeTo(null);
		//windowProblem.setVisible(true);
		windowProblem.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//1. Problem description panel
		JPanel windowProblem_panel = new JPanel();
		windowProblem_panel.setLayout(new BorderLayout());
		windowProblem.add(windowProblem_panel);
		
		//1.1. Problem panel
		JPanel problem_panel = new JPanel();
		problem_panel.setLayout(new BorderLayout());
		windowProblem_panel.add(problem_panel, BorderLayout.NORTH);
		
		JLabel problem_lb = new JLabel("    Problem:  ");
		problem_panel.add(problem_lb, BorderLayout.WEST);
		JTextField problem_tf = new JTextField();
		problem_panel.add(problem_tf, BorderLayout.CENTER);
		
		//1.2. Problem description panel
		JPanel problemDescription_panel = new JPanel();
		problemDescription_panel.setLayout(new BorderLayout());
		windowProblem_panel.add(problemDescription_panel, BorderLayout.CENTER);
		
		JLabel problemDescription_lb = new JLabel(" Description: ");
		problemDescription_panel.add(problemDescription_lb, BorderLayout.WEST);
		JTextArea problemDescription_tf = new JTextArea();
		problemDescription_panel.add(problemDescription_tf, BorderLayout.CENTER);
		
		//1.3. Help and Continue panel - Buttons
		JPanel help_continue_panel = new JPanel();
		help_continue_panel.setLayout(new BorderLayout());
		problemDescription_panel.add(help_continue_panel, BorderLayout.SOUTH);
		
		JButton help_button = new JButton("Help");
		help_button.setForeground(Color.RED);
		help_continue_panel.add(help_button, BorderLayout.WEST);
		JButton confirm_button = new JButton("Confirm ➜");
		help_continue_panel.add(confirm_button, BorderLayout.EAST);
		
		help_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(null, "Help!");
				windowHelp.setVisible(true);
			}
		});
		
		confirm_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Confirmed!");
			}
		});
	}
	
	private void help_window () {
		//Help window
		windowHelp = new JFrame("Help");
		windowHelp.setSize(600, 350);
		windowHelp.setLocationRelativeTo(null);
		//windowHelp.setVisible(true);
		windowHelp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//1. Help panel - Tab's
		JTabbedPane help_tp =new JTabbedPane();
		windowHelp.add(help_tp);
		
		JPanel frequent_questions_panel = new JPanel();
		JPanel email_panel = new JPanel();
		email_panel.setLayout(new BorderLayout());
		
		help_tp.add("Frequent Questions", frequent_questions_panel);
		help_tp.add("Question by E-mail", email_panel);
		
		//1.1. Frequent Questions
		Read_FrequentQuestions read_fq = new Read_FrequentQuestions();
		
		ArrayList<FrequentQuestions> frequentQuestions = read_fq.getFrequentQuestions();
		
		int n_questions = frequentQuestions.size();
		
		frequent_questions_panel.setLayout(new GridLayout(n_questions, 1));
		
		for (FrequentQuestions fq : frequentQuestions) {
			JButton fq_button = new JButton(fq.getNumber() + " " + fq.getQuestion());
			frequent_questions_panel.add(fq_button);
			
			fq_button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Resposta: " + fq.getAnswer());
				}
			});
		}
		
		//1.2. Question by E-mail 
		JPanel email_tf_panel = new JPanel();
		email_tf_panel.setLayout(new BorderLayout());
		email_panel.add(email_tf_panel, BorderLayout.NORTH);
		
		JPanel from_panel = new JPanel();
		from_panel.setLayout(new BorderLayout());
		email_tf_panel.add(from_panel, BorderLayout.NORTH);
		JLabel from_jl = new JLabel("From:     ");
		from_panel.add(from_jl, BorderLayout.WEST);
		JTextField from_tf = new JTextField();
		from_panel.add(from_tf, BorderLayout.CENTER);
		
		JPanel subject_panel = new JPanel();
		subject_panel.setLayout(new BorderLayout());
		email_tf_panel.add(subject_panel, BorderLayout.CENTER);
		JLabel subject_jl = new JLabel("Subject: ");
		subject_panel.add(subject_jl, BorderLayout.WEST);
		JTextField subject_tf = new JTextField();
		subject_panel.add(subject_tf, BorderLayout.CENTER);
		
		JPanel body_panel = new JPanel();
		body_panel.setLayout(new BorderLayout());
		email_panel.add(body_panel, BorderLayout.CENTER);
		JTextArea body_tf = new JTextArea();
		body_panel.add(body_tf, BorderLayout.CENTER);
		
		//...
		JPanel email_button_panel = new JPanel();
		email_button_panel.setLayout(new BorderLayout());
		email_panel.add(email_button_panel, BorderLayout.SOUTH);
		
		JButton send_button = new JButton("Send");
		email_button_panel.add(send_button, BorderLayout.EAST);
		
		send_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(from_tf.getText().equals("") || subject_tf.getText().equals("") || body_tf.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Fields can not be empty.");
				} else {
					@SuppressWarnings("unused")
					Send_Email send_Email = new Send_Email(from_tf.getText(), subject_tf.getText(), body_tf.getText());
				}
			}
		});
	}
}
