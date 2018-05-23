package jMetal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import GUI.*;

/* Implementa��o de um problema do tipo Double que executa o .jar externo
   Kursawe.jar e pode ser usado como um dos problema de teste indicados 
   no encunciado do trabalho */

@SuppressWarnings("serial")
public class MyProblemDoubleExternalViaJAR extends AbstractDoubleProblem {

	private static int n_variables = GUI.getN_variables();

	private static JTable variableTable;

	public MyProblemDoubleExternalViaJAR() {
		// 10 variables (anti-spam filter rules) by default 
		// this(10);
		this(n_variables);
	}

	public MyProblemDoubleExternalViaJAR(Integer numberOfVariables) {
		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(2);
		setName("MyProblemDoubleExternalViaJAR");

		List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
		List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

		variableTable = GUI.getVariableName_table();

		for (int i = 0; i < getNumberOfVariables(); i++) {			
			double min = (double) variableTable.getModel().getValueAt(i, 1);
			double max = (double) variableTable.getModel().getValueAt(i, 2);
			lowerLimit.add(min);
			upperLimit.add(max);
		}

		//		for (int i = 0; i < getNumberOfVariables(); i++) {
		//			lowerLimit.add(-5.0);
		//			upperLimit.add(5.0);
		//		}

		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);	    	    
	}

	public void evaluate(DoubleSolution solution){
		String solutionString ="";
		String evaluationResultString ="";
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			solutionString = solutionString + " " + solution.getVariableValue(i);  
		}
		try {
			String line;
			System.out.println(solutionString);
			Process p = Runtime.getRuntime().exec("java -jar " + GUI.getFilePath() + " " + solutionString);
			BufferedReader brinput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = brinput.readLine()) != null) {
				evaluationResultString+=line;
			}
			brinput.close();
			p.waitFor();
		}
		catch (Exception err) { 
			err.printStackTrace(); 
		}
		
		String[] individualEvaluationCriteria = evaluationResultString.split("\\s+");
		// It is assumed that all evaluated criteria are returned in the same result string
		for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
			solution.setObjective(i, Double.parseDouble(individualEvaluationCriteria[i]));
		}	
	}
}
