package Users;

import javax.swing.JOptionPane;

public class User extends General_user {

	public User(String username, String password, String type) {
		super(username, password, "U");
	}

	/**
	 * @return the password
	 */
	@Override public String getPassword() {
		JOptionPane.showMessageDialog(null, "Only the administrator has access to this information");
		return null;
	}

	/**
	 * @param password to set
	 */
	public void setPassword(String password) {
		JOptionPane.showMessageDialog(null, "Only the administrator has access to this information");
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		JOptionPane.showMessageDialog(null, "Only the administrator has access to this information");
		return null;
	}

	/**
	 * @param username to set
	 */
	public void setUsername(String username) {
		JOptionPane.showMessageDialog(null, "Only the administrator has access to this information");
	}

}
