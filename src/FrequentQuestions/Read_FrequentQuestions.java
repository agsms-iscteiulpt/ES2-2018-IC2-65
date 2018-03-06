package FrequentQuestions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Read_FrequentQuestions {
	
	private ArrayList<FrequentQuestions> frequentQuestions = new ArrayList<>();
	
	public Read_FrequentQuestions() {
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(new FileReader("FrequentQuestions.txt"))
					.useDelimiter(" ");
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split("/");
				String number = data[0];
				String question = data[1];
				String answer = data[2];

				FrequentQuestions fq = new FrequentQuestions(number, question, answer);
				frequentQuestions.add(fq);
				
			}
			System.out.println(frequentQuestions);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<FrequentQuestions> getFrequentQuestions() {
		return frequentQuestions;
	}
}
