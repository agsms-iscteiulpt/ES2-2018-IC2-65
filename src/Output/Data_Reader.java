package Output;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Data_Reader{

	private static ArrayList<Solution> solutions = new ArrayList<>();

	public Data_Reader(String fileName, String algorithm) throws Exception {
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(new FileReader(fileName)).useDelimiter(" ");
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split(" ");
				String crt1 = data[0];
				String crt2 = data[1];

				Solution s = new Solution(algorithm, crt1, crt2);
				solutions.add(s);
			}
			//			System.out.println(solutions);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the solutions
	 */
	public static ArrayList<Solution> getSolutions() {
		return solutions;
	}
}
