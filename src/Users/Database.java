package Users;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {

	private ArrayList<General_user> Users = new ArrayList<>();
	private ArrayList<General_user> Admins = new ArrayList<>();

	public Database() {
		read_database();
	}

	@SuppressWarnings("resource")
	private void read_database()  {	
		try {
			Scanner scanner = new Scanner(new FileReader(System.getProperty("user.home") + "/git/ES2-2018-IC2-65/Database.txt"))
					.useDelimiter(" ");
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split(" ");
				String type = data[0];
				String username = data[1];
				String password = data[2];

				if(type.equals("A")) {
					General_user admin = new Admin(username, password, type);
					Admins.add(admin);
				} else {
					General_user user = new User(username, password, type);
					Users.add(user);
				}
			}
			System.out.println(Users);
			System.out.println(Admins);
			//		scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<General_user> getAdmins() {
		return Admins;
	}
	
	public ArrayList<General_user> getUsers() {
		return Users;
	}

}
