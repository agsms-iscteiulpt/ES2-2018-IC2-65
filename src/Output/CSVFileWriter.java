package Output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import GUI.GUI;

public class CSVFileWriter {

	private static ArrayList<String> algoritmsChecked = GUI.getAlgoritmsChecked();

	private String problem_type = GUI.getProblem_type_selected();

	private ArrayList<Solution> solutions = Data_Reader.getSolutions();

	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	//CSV file header
	private static final String FILE_HEADER = "Algorithm," + GUI.getCriteria1_name() + "," + GUI.getCriteria2_name();

	public void start() throws Exception {
		switch (problem_type) {
		case "Double":
			for (String algorithm : algoritmsChecked) {
				String filePath = System.getProperty("user.home") + "/git/ES2-2018-IC2-65/experimentBaseDirectory/ExperimentsDoubleExternalViaJAR/data/" + algorithm + "/MyProblemDoubleExternalViaJAR/BEST_HV_FUN.tsv";
				new Data_Reader(filePath, algorithm);
			}
			break;
		case "Integer":
			for (String algorithm : algoritmsChecked) {
				String filePath = System.getProperty("user.home") + "/git/ES2-2018-IC2-65/experimentBaseDirectory/ExperimentsIntegerExternalViaJAR/data/" + algorithm + "/MyProblemIntegerExternalViaJAR/BEST_HV_FUN.tsv";
				new Data_Reader(filePath, algorithm);
			}
			break;
		case "Binary":
			for (String algorithm : algoritmsChecked) {
				String filePath = System.getProperty("user.home") + "/git/ES2-2018-IC2-65/experimentBaseDirectory/ExperimentsBinaryExternalViaJAR/data/" + algorithm + "/MyProblemBinaryExternalViaJAR/BEST_HV_FUN.tsv";
				new Data_Reader(filePath, algorithm);
				break;
			}

			//		/Users/MafaldaBarreirosCardoso/git/ES2-2018-IC2-65/experimentBaseDirectory/ExperimentsDoubleExternalViaJAR/data/NSGAII/MyProblemDoubleExternalViaJAR/BEST_HV_FUN.tsv

		}

		FileWriter fileWriter = null;

		try {
//			fileWriter = new FileWriter(new File(System.getProperty("user.home") +"/Desktop/bestSolutions.csv"));
			fileWriter = new FileWriter(new File("bestSolutions.csv"));

			
			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Write a new student object list to the CSV file
			for (Solution solution : solutions) {
				fileWriter.append(String.valueOf(solution.getAlgorithm()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(solution.getCriteria1());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(solution.getCriteria2());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CSVFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}
}