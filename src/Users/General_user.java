package Users;

public class General_user {
	
	private String username;
	private String password;
	
	public General_user(String username, String password ) {
		this.username = username;
		this.password = password;
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

}
