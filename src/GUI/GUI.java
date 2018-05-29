package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import Email.Send_Email;
import FrequentQuestions.FrequentQuestions;
import FrequentQuestions.Read_FrequentQuestions;
import Output.CSVFileWriter;
//import Output.CSVFileWriter;
import Users.Database;
import Users.General_user;
import XML.XMLReader;
import XML.XMLWriter;
import jMetal.OptimizationProcess;

public class GUI implements ActionListener {

	private JFrame windowAutentication;
	private JFrame windowProblem;
	private JFrame windowHelp;
	private JFrame windowAlgoritm;
	private JFrame windowDecisionVariable;

	private JPanel algoritm_panel;
	private JPanel problemType_and_Algoritms_panel;
	private JPanel ProblemAlgoritms_panel;
	private JPanel decisionVariable_panel;

	private JCheckBox admin_cb;
	private JCheckBox user_cb;

	private JProgressBar progressBar;

	private static String problem_type_selected;
	private JRadioButton problem_type_binary;
	private JRadioButton problem_type_integer;
	private JRadioButton problem_type_double;
	private ButtonGroup groupProblems;
	private static JCheckBox cb_algoritm;
	private static ArrayList<String> algoritmsChecked = new ArrayList<>();
	private ArrayList<JCheckBox> cb_algoritmsChecked = new ArrayList<>();
	private ArrayList<JCheckBox> algoritms_list = new ArrayList<>();

	private JTextField username_tf;
	private JPasswordField password_pf;
	
	private String email = null;
	private String fileNameXML;
	private String filePathXML;

	private static String criteria1_name;
	private static String criteria2_name;
	private static int n_variables;
	private JTextField nVariable_tf;
	private boolean correctFile;
	private boolean correctNumber;

	private static File fileJAR;
	private static File fileXML;
	private static JTextField textFieldFile;
	private static JTable variableName_table;

	@SuppressWarnings("unused")
	private Database database;
	private ArrayList<General_user> admins;
	private ArrayList<General_user> users;

	String[] ProblemType = new String[]{"Binary", "Double", "Integer"};

	/**
	 * Constructor
	 */
	public GUI() {
		Database database = new Database();

		admins = database.getAdmins();
		users = database.getUsers();

		login_window();
		problem_description_window();
		help_window();
		algoritm_window();

		windowAutentication.pack();
	}

	/**
	 * Creates log in window
	 */
	private void login_window() {
		//Log in window
		windowAutentication = new JFrame("Log in");
		windowAutentication.setSize(230, 140);
		windowAutentication.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		windowAutentication.setLocationRelativeTo(null);
		windowAutentication.setVisible(true);

		//1. Log in window panel
		JPanel login_panel = new JPanel();
		login_panel.setLayout(new BorderLayout());
		windowAutentication.getContentPane().add(login_panel);

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

		JButton cancel_button = new JButton("Cancel");
		confirm_panel.add(cancel_button);
		JButton confirm_button = new JButton("OK");
		confirm_panel.add(confirm_button);

		cancel_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(windowAutentication, "Are you sure you want to close the application?", "Please Confirm", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});

		confirm_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(user_exists() == true) {
//					JOptionPane.showMessageDialog(null, "Succeeded!");
					
					JTextField tf_email = new JTextField();
					Object[] message = {
							"Email:", tf_email,
					};
					int option = JOptionPane.showConfirmDialog(null, message, "Enter your email", JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION && !tf_email.getText().equals("")) {
						windowProblem.setVisible(true);
						setEmail(tf_email.getText());
					} else {
						JOptionPane.showMessageDialog(null, "Need to introduce an email for application purposes!");
					}
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
					JOptionPane.showMessageDialog(null, "Wrong permissions! You're an administrator!", "Message", JOptionPane.ERROR_MESSAGE);
			}

			if(admin_cb.isSelected()) {
				for (General_user  user: users) {													
					if(user.getUsername().equals(username_tf.getText()) && user.getPassword().equals(password_pf.getText())) 
						JOptionPane.showMessageDialog(null, "Wrong permissions! You are a user!", "Message", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Wrong password or username! \n Please try again!", "Message", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Creates problem description window
	 */
	private void problem_description_window() {
		//Problem description window
		windowProblem = new JFrame("Problem description");
		windowProblem.setSize(600, 350);
		windowProblem.setLocationRelativeTo(null);
		windowProblem.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//1. Problem description panel
		JPanel windowProblem_panel = new JPanel();
		windowProblem_panel.setLayout(new BorderLayout());
		windowProblem.getContentPane().add(windowProblem_panel);

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
		JButton confirm_button = new JButton("Confirm >");
		help_continue_panel.add(confirm_button, BorderLayout.EAST);

		help_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				windowHelp.setVisible(true);
			}
		});

		confirm_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField criteria1 = new JTextField("Criteria1");
				JTextField criteria2 = new JTextField("Criteria2");
				Object[] message = {
						"Quality criteria 1:", criteria1,
						"Quality criteria 2:", criteria2,
				};
				int option = JOptionPane.showConfirmDialog(null, message, "Name quality criteria", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					windowAlgoritm.setVisible(true);
					criteria1_name = criteria1.getText();
					criteria2_name = criteria2.getText();
				}
			}
		});
	}

	/**
	 * Creates help window
	 */
	private void help_window () {
		//Help window
		windowHelp = new JFrame("Help");
		windowHelp.setSize(600, 350);
		windowHelp.setLocationRelativeTo(null);

		//1. Help panel - Tab's
		JTabbedPane help_tp =new JTabbedPane();
		windowHelp.getContentPane().add(help_tp);

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
				if(subject_tf.getText().equals("") || body_tf.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Fields can not be empty.");
				} else {
					@SuppressWarnings("unused")
					Send_Email send_Email = new Send_Email(email, subject_tf.getText(), body_tf.getText());
				}
			}
		});
	}

	/**
	 * Creates algorithm window
	 */
	private void algoritm_window () {
		windowAlgoritm = new JFrame("Algorithm");
		windowAlgoritm.setSize(350, 350);
		windowAlgoritm.setLocationRelativeTo(null);
		windowAlgoritm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//Algoritm panel
		algoritm_panel = new JPanel();
		algoritm_panel.setLayout(new BorderLayout());
		windowAlgoritm.add(algoritm_panel);

		//Time and number of variables panel
		JPanel time_variable_panel = new JPanel();
		time_variable_panel.setLayout(new BorderLayout());
		algoritm_panel.add(time_variable_panel, BorderLayout.NORTH);

		JPanel time_panel = new JPanel();
		time_panel.setLayout(new BorderLayout());
		time_variable_panel.add(time_panel, BorderLayout.CENTER);

		JLabel time_lb = new JLabel("Time", JLabel.CENTER);
		//		time_lb.setVerticalAlignment(SwingConstants.CENTER);
		time_panel.add(time_lb, BorderLayout.NORTH);

		JPanel time_lables = new JPanel();
		time_lables.setLayout(new GridLayout(2, 1));
		JLabel time_max_lb = new JLabel("   Max: ");
		time_max_lb.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel time_ideal_lb = new JLabel("   Ideal: ");
		time_ideal_lb.setHorizontalAlignment(SwingConstants.CENTER);
		time_lables.add(time_max_lb);
		time_lables.add(time_ideal_lb);
		time_panel.add(time_lables, BorderLayout.CENTER);

		JPanel time_textfield = new JPanel();
		time_textfield.setLayout(new GridLayout(2, 2));
		JTextField time_max_tf = new JTextField(10);
		JLabel empty = new JLabel(" ");
		JTextField time_ideal_tf = new JTextField(10);
		JLabel empty1 = new JLabel(" ");
		time_textfield.add(time_max_tf);
		time_textfield.add(empty);
		time_textfield.add(time_ideal_tf);
		time_textfield.add(empty1);
		time_panel.add(time_textfield, BorderLayout.EAST);

		JPanel nVariable_panel = new JPanel();
		time_variable_panel.add(nVariable_panel, BorderLayout.SOUTH);

		JLabel nVariable_lb = new JLabel("Number of Variables: ");
		nVariable_tf = new JTextField(2);
		JButton readXML_button = new JButton("Upload XML file");
		nVariable_panel.add(nVariable_lb);
		nVariable_panel.add(nVariable_tf);
		nVariable_panel.add(readXML_button);

		readXML_button.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);
				//selecionar somente ficheiros
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileXML = fileChooser.getSelectedFile();

				XMLReader xml = new XMLReader(fileXML);

				String problemType = xml.getProblemType();
				ArrayList<String> algoritms = xml.getAlgoritms();
				int n_variable = xml.getN_variables();
				String jarPath = xml.getJar_path();

				if (problemType.equals("Binary")) {
					problem_type_binary.setSelected(true);
					problem_type_binary.doClick();
				} else if (problemType.equals("Integer")) {
					problem_type_integer.setSelected(true);
					problem_type_integer.doClick();
				} else if (problemType.equals("Double")) {
					problem_type_double.setSelected(true);
					problem_type_double.doClick();
				}

				for (JCheckBox cb_algoritm : algoritms_list) {
					for (String algoritm_string : algoritms) {
						if(cb_algoritm.getLabel().equals(algoritm_string)) {
							algoritmsChecked.add(algoritm_string);
							cb_algoritmsChecked.add(cb_algoritm);
						}
					}
				}

				for (JCheckBox cb_algoritmChecked : cb_algoritmsChecked) {
					JCheckBox checkBox = cb_algoritmChecked;
					checkBox.doClick();
					//					fireContentsChanged(this, index, index);
				}
				nVariable_tf.setText(Integer.toString(n_variable));

				textFieldFile.setText(jarPath);
			}
		});

		//Problem type and algoritm panel
		problemType_and_Algoritms_panel = new JPanel();
		problemType_and_Algoritms_panel.setLayout(new BorderLayout());
		algoritm_panel.add(problemType_and_Algoritms_panel, BorderLayout.CENTER);

		problemType_and_Algoritms();

		//Read and save, Help and execute file panel
		JPanel bottom_panel = new JPanel();
		bottom_panel.setLayout(new BorderLayout());
		algoritm_panel.add(bottom_panel, BorderLayout.SOUTH);

		JPanel read_save_panel = new JPanel();
		read_save_panel.setLayout(new BorderLayout());
		bottom_panel.add(read_save_panel, BorderLayout.NORTH);

		JButton readFile_button = new JButton("Read .JAR File");
		read_save_panel.add(readFile_button, BorderLayout.WEST);

		readFile_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);
				//selecionar somente ficheiros
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileJAR = fileChooser.getSelectedFile();
				textFieldFile.setText(fileJAR.getAbsolutePath());
			}
		});

		JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		textFieldFile = new JTextField();
		textFieldFile.setEnabled(false);

		JPanel filePath_panel = new JPanel();
		filePath_panel.setLayout(new BoxLayout(filePath_panel, BoxLayout.Y_AXIS));

		BoundedRangeModel brm = textFieldFile.getHorizontalVisibility();
		scrollBar.setModel(brm);
		filePath_panel.add(textFieldFile);
		filePath_panel.add(scrollBar);

		read_save_panel.add(filePath_panel, BorderLayout.SOUTH);

		JButton saveFile_button = new JButton("Save XML File");
		read_save_panel.add(saveFile_button, BorderLayout.EAST);
		saveFile_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				algoritmsChecked();
				if(checkIfAllRight() == true) {
					String n_variables_String = nVariable_tf.getText();
					new XMLWriter(problem_type_selected, algoritmsChecked, n_variables_String, getFileJARPath());
				}
			}
		});

		JPanel help_execute_panel = new JPanel();
		help_execute_panel.setLayout(new BorderLayout());
		bottom_panel.add(help_execute_panel, BorderLayout.SOUTH);

		JButton help_button = new JButton("Help");
		help_button.setForeground(Color.RED);
		help_execute_panel.add(help_button, BorderLayout.WEST);
		JButton execute_button = new JButton("EXECUTE");
		help_execute_panel.add(execute_button, BorderLayout.EAST);
		execute_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				algoritmsChecked();
				if(checkIfAllRight()) {
					String n_variables_String = nVariable_tf.getText();
					new XMLWriter(problem_type_selected, algoritmsChecked, n_variables_String, getFileJARPath());
					decisionVariable_window();
					windowDecisionVariable.setVisible(true);
				}
				fileNameXML = XMLWriter.getFileName();
				filePathXML = XMLWriter.getFilePath();
			}
		});

		help_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				windowHelp.setVisible(true);
			}
		});
	}

	/**
	 * Checks if type of problem, algorithms, number of variables and file are inserted
	 * @return boolean
	 */
	private boolean checkIfAllRight () {
		String n_variables_String = nVariable_tf.getText();
		String file_Name = textFieldFile.getText();
		correctFile = false;
		correctNumber = false;
		if (correctFile == false || correctNumber == false) {
			while (correctFile == false) {
				if(file_Name.equals("") && correctFile == false) {
					JOptionPane.showMessageDialog(null, "You need to insert a file!");
					correctFile = true;
				} 
				correctFile = true;
			}

			while (correctNumber == false) {	
				if(n_variables_String.equals("") && correctNumber == false) {
					JOptionPane.showMessageDialog(null, "Number of variables missing!");
					correctNumber = true;
				}
				correctNumber = true;	
			}
		}

		if (problemWasChosen() == false) {
			JOptionPane.showMessageDialog(null, "Need to choose type of problem!");
		}

		if (algoritmWasChosen() == false) {
			JOptionPane.showMessageDialog(null, "Need to choose algoritms!");
		}

		if (!file_Name.equals("") && isNumber(n_variables_String) == true && problemWasChosen() == true && algoritmWasChosen() == true) {
			correctFile = true;
			correctNumber = true;
			n_variables = Integer.parseInt(n_variables_String);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if number inserted it's a number or not
	 * @param n_variables_String
	 * @return boolean
	 */
	private boolean isNumber (String n_variables_String) {
		boolean number = false;
		if(!n_variables_String.equals("")) {
			while (number == false) {
				if (n_variables_String.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
					n_variables = Integer.parseInt(n_variables_String);
					number = true;
					return true;
				} else {
					JOptionPane.showMessageDialog(null, "It's not a number!");
					number = true;
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Check if any problem type was chosen
	 * @return boolean
	 */
	private boolean problemWasChosen () {
		if (problem_type_selected != null) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if any algoritm was chosen
	 * @return boolean
	 */
	private boolean algoritmWasChosen () {
		if (!algoritmsChecked.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Creates a panel with problem type and algoritms associated
	 */
	private void problemType_and_Algoritms () {
		//Problem Type
		JPanel problemType_panel = new JPanel();
		problemType_panel.setLayout(new GridLayout(0, 1));
		problemType_and_Algoritms_panel.add(problemType_panel, BorderLayout.CENTER);

		groupProblems = new ButtonGroup();

		problem_type_binary = new JRadioButton("Binary");
		problem_type_double = new JRadioButton("Double");
		problem_type_integer = new JRadioButton("Integer");
		
		problem_type_binary.setActionCommand("Binary");
		problem_type_double.setActionCommand("Double");
		problem_type_integer.setActionCommand("Integer");
		
		groupProblems.add(problem_type_binary);
		groupProblems.add(problem_type_double);
		groupProblems.add(problem_type_integer);
		
		problemType_panel.add(problem_type_binary);
		problemType_panel.add(problem_type_double);
		problemType_panel.add(problem_type_integer);

		//Register a listener for the radio buttons
		problem_type_binary.addActionListener(this);
		problem_type_integer.addActionListener(this);
		problem_type_double.addActionListener(this);
	}

	/**
	 * Listens to the radio buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String type_name = e.getActionCommand();

		JPanel oldProblemAlgoritms_panel = ProblemAlgoritms_panel;

		switch (type_name) {
		case "Binary":
			ProblemAlgoritms_panel = show_algoritms(jMetal.OptimizationProcess.getAlgorithsForBinaryProblemType());
			problem_type_selected = "Binary";
			break;
		case "Double":
			ProblemAlgoritms_panel = show_algoritms(jMetal.OptimizationProcess.getAlgorithsForDoubleProblemType());
			problem_type_selected = "Double";
			break;
		case "Integer":
			ProblemAlgoritms_panel = show_algoritms(jMetal.OptimizationProcess.getAlgorithsForIntegerProblemType());
			problem_type_selected = "Integer";
			break;
		default:
			break;
		}

		if (oldProblemAlgoritms_panel != null) {
			problemType_and_Algoritms_panel.remove(oldProblemAlgoritms_panel);
		}

		problemType_and_Algoritms_panel.add(ProblemAlgoritms_panel, BorderLayout.EAST);
		problemType_and_Algoritms_panel.revalidate();
		problemType_and_Algoritms_panel.repaint();
	}

	/**
	 * Returns a panel with algoritms of an especific problem type
	 * @param algoritmsList
	 * @return ProblemAlgoritms_panel
	 */
	public JPanel show_algoritms (String [] algoritmsList) {
		//Algoritms of an especific problem type
		ProblemAlgoritms_panel = new JPanel();
		ProblemAlgoritms_panel.setLayout(new GridLayout(0, 1));
		problemType_and_Algoritms_panel.add(ProblemAlgoritms_panel, BorderLayout.EAST);

		for (String algoritm : algoritmsList) {
			cb_algoritm = new JCheckBox(algoritm);
			ProblemAlgoritms_panel.add(cb_algoritm);
			algoritms_list.add(cb_algoritm);
		}

		return ProblemAlgoritms_panel;
	}

	/**
	 * Creates decision variable window
	 */
	private void decisionVariable_window () {
		windowDecisionVariable = new JFrame("Decision Variable");
		windowDecisionVariable.setSize(600, 350);
		windowDecisionVariable.setLocationRelativeTo(null);
		windowDecisionVariable.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		decisionVariable_panel = new JPanel();
		decisionVariable_panel.setLayout(new BorderLayout());
		windowDecisionVariable.add(decisionVariable_panel);

		JTextField tf_variablegroupName = new JTextField("Name of decision variables (group)");
		decisionVariable_panel.add(tf_variablegroupName, BorderLayout.NORTH);

		JButton ok_button = new JButton("OK");
		decisionVariable_panel.add(ok_button, BorderLayout.EAST);

		progressBar = new JProgressBar(0, n_variables * 1000 * algoritmsChecked.size());
		decisionVariable_panel.add(progressBar, BorderLayout.SOUTH);

		variableName_table_data ();

		ok_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				new Send_Email(email, problem_type_selected, fileNameXML, filePathXML);
				
				new OptimizationProcess(problem_type_selected, progressBar);

				progressBar.setValue(n_variables * 1000 * algoritmsChecked.size());
				progressBar.update(progressBar.getGraphics());
				JOptionPane.showMessageDialog(null, "Finished!");
				
				try {
					CSVFileWriter csv = new CSVFileWriter();
					csv.start();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates variable table
	 */
	private void variableName_table_data () {
		JPanel variableName_table_panel = new JPanel();
		variableName_table_panel.setLayout(new GridLayout(1, 1));
		decisionVariable_panel.add(variableName_table_panel, BorderLayout.CENTER);

		//ADD SCROLLPANE
		JScrollPane scrollPane = new JScrollPane();
		variableName_table_panel.add(scrollPane);

		//THE TABLE
		variableName_table = new JTable();
		scrollPane.setViewportView(variableName_table);

		//THE MODEL OF OUR TABLE
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(){
			public Class<?> getColumnClass(int column){
				switch(column){
				case 0:
					return String.class;
				case 1:
					return Double.class;
				case 2:
					return Double.class;
				default:
					return String.class;
				}
			}
		};

		//ASSIGN THE MODEL TO TABLE
		variableName_table.setModel(model);

		model.addColumn("Decision Variable");
		model.addColumn("Min");
		model.addColumn("Max");

		//THE ROW
		for(int i=0;i<n_variables;i++) {
			model.addRow(new Object[0]);
			model.setValueAt("Variable " + (i+1),i,0);
			if (problem_type_selected.equals("Double")) {
				model.setValueAt(-5.0,i,1);
				model.setValueAt(+5.0,i,2);
			} else if (problem_type_selected.equals("Integer")) {
				model.setValueAt(-1000,i,1);
				model.setValueAt(+1000,i,2);
			} else if (problem_type_selected.equals("Binary")) {
				model.setValueAt(0,i,1);
				model.setValueAt(1,i,2);
			}
		}

		variableName_table.setPreferredScrollableViewportSize(variableName_table.getPreferredSize());
		variableName_table.setFillsViewportHeight(true);

		windowAlgoritm.setVisible(true);

	}

	/**
	 * Checks which checkbox is selected and puts it on the list
	 */
	@SuppressWarnings("deprecation")
	private void algoritmsChecked () {
		for (JCheckBox algoritm : algoritms_list) {
			if(algoritm.isSelected()==true) {
				algoritmsChecked.add(algoritm.getLabel());
				algoritmsChecked = (ArrayList<String>) algoritmsChecked.stream().distinct().collect(Collectors.toList());
			}
		}
	}	

	/**
	 * Returns an ArrayList with algoritms that were checked by client
	 * @return algoritmsChecked
	 */
	public static ArrayList<String> getAlgoritmsChecked() {
		return algoritmsChecked;
	}

	/**
	 * Returns file path
	 * @return file Absolute Path
	 */
	public static String getFileJARPath() {
		return textFieldFile.getText();
	}

	/**
	 * Returns number of variables chosen
	 * @return n_variables
	 */
	public static int getN_variables() {
		return n_variables;
	}

	/**
	 * Returns Variables JTable 
	 * @return variableName_table
	 */
	public static JTable getVariableName_table() {
		return variableName_table;
	}

	/**
	 * Returns problem type that is selected
	 * @return problem_type_selected
	 */
	public static String getProblem_type_selected() {
		return problem_type_selected;
	}

	/**
	 * Returns name of criteria 1
	 * @return criteria1_name
	 */
	public static String getCriteria1_name() {
		return criteria1_name;
	}

	/**
	 * Returns name of criteria 2
	 * @return criteria2_name
	 */
	public static String getCriteria2_name() {
		return criteria2_name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
