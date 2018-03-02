package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUI {
	
	private JFrame windowAutentication;
	private JFrame windowProblem;
	
	public GUI() {
		//Janela de autenticacao
		windowAutentication = new JFrame("Autenticacao");
		windowAutentication.setSize(350, 150);
		windowAutentication.setVisible(true);
		windowAutentication.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		windowAutentication.pack();
		
		//Janela de descricao do problema
		windowProblem = new JFrame("Descricao do Problema");
		windowProblem.setSize(1000, 650);
		//windowProblem.setVisible(true);
		windowProblem.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		addcontent();
	}

	private void addcontent() {
		//Conteudo janela autenticacao
		JPanel autenticacao_panel = new JPanel();
		autenticacao_panel.setLayout(new BorderLayout());
		windowAutentication.add(autenticacao_panel);
		
		JPanel username_pass_panel = new JPanel();
		username_pass_panel.setLayout(new GridLayout(2,2,3,5));
		autenticacao_panel.add(username_pass_panel, BorderLayout.CENTER);
		
		JLabel username_lb = new JLabel("Username: ");
		username_pass_panel.add(username_lb);
		JTextField username_tf = new JTextField();
		username_pass_panel.add(username_tf);
		JLabel password_lb = new JLabel("Password: ");
		username_pass_panel.add(password_lb);
		JTextField password_tf = new JTextField();
		username_pass_panel.add(password_tf);
		
		JPanel admin_user_panel = new JPanel();
		autenticacao_panel.add(admin_user_panel, BorderLayout.SOUTH);
		
		JLabel admin_jl = new JLabel("Administrador: ");
		admin_user_panel.add(admin_jl);
		JCheckBox admin_cb = new JCheckBox();
		admin_user_panel.add(admin_cb);
		JLabel user_jl = new JLabel("Utilizador: ");
		admin_user_panel.add(user_jl);
		JCheckBox user_cb = new JCheckBox();
		admin_user_panel.add(user_cb);
		
		
		
	}
	
}
