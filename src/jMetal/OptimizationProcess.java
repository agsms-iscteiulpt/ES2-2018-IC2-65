package jMetal;

import javax.swing.JProgressBar;

public class OptimizationProcess {

	/* O conjunto de algoritmos adequados a cada tipo de problema estao indicados aqui */
	static String[] AlgorithsForDoubleProblemType = new String[]{"NSGAII","SMSEMOA","GDE3","IBEA","MOCell","MOEAD","PAES","RandomSearch"};
	static String[] AlgorithsForIntegerProblemType = new String[]{"NSGAII","SMSEMOA","MOCell","PAES","RandomSearch"};
	static String[] AlgorithsForBinaryProblemType = new String[]{"NSGAII","SMSEMOA","MOCell","MOCH","PAES","RandomSearch","SPEA2"};
	
	public OptimizationProcess(String type_of_problem, JProgressBar progressBar) {
		System.out.println("Starting optimization process");
		
		try {
			switch (type_of_problem) {
			case "Double":
				System.out.println("Working...");
				new ExperimentsDoubleExternalViaJAR(progressBar);
				break;
			case "Integer":
				System.out.println("Working...");
				new ExperimentsIntegeExternalViaJAR(progressBar);
				break;
			case "Binary":
				System.out.println("Working...");
				new ExperimentsBinaryExternalViaJAR(progressBar);	
				break;
			}
			System.out.println("Optimization process finished");
		} catch (Exception e) {
//			System.out.println(e);
			e.printStackTrace();
			// TODO: handle exception
		}
	} 

	public static String[] getAlgorithsForBinaryProblemType() {
		return AlgorithsForBinaryProblemType;
	}

	public static String[] getAlgorithsForDoubleProblemType() {
		return AlgorithsForDoubleProblemType;
	}

	public static String[] getAlgorithsForIntegerProblemType() {
		return AlgorithsForIntegerProblemType;
	}
}
