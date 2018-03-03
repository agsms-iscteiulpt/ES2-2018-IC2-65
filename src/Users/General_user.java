package Users;

public class General_user {
	
	private String username;
	private String password;
	private String type;
	
	public General_user(String username, String password, String type) {
		this.username = username;
		this.password = password;
		this.type = type;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "[" + type + ", " + username + ", " + password + "]";
	}
}
