package jMetal;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import org.uma.jmetal.util.JMetalException;

import GUI.GUI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.BitSet;

/* Implementa��o de um problema do tipo Binary que executa o .jar externo
   OneZeroMax.jar e pode ser usado como um dos problema de teste indicados 
   no encunciado do trabalho */

@SuppressWarnings("serial")
public class MyProblemBinaryExternalViaJAR extends AbstractBinaryProblem {
	private int bits ;

	private static int n_variables = GUI.getN_variables();

	public MyProblemBinaryExternalViaJAR() throws JMetalException {
		// 10 decision variables by default  
		// this(10);
		this(n_variables);
	}

	public MyProblemBinaryExternalViaJAR(Integer numberOfBits) throws JMetalException {
		setNumberOfVariables(1);
		setNumberOfObjectives(2);
		setName("MyProblemBinaryExternalViaJAR");
		bits = numberOfBits ;

	}

	@Override
	protected int getBitsPerVariable(int index) {
		if (index != 0) {
			throw new JMetalException("Problem MyBinaryProblem has only a variable. Index = " + index) ;
		}
		return bits ;
	}

	@Override
	public BinarySolution createSolution() {
		return new DefaultBinarySolution(this) ;
	}

	@Override
	public void evaluate(BinarySolution solution){

		String solutionString ="";
		String evaluationResultString ="";
		BitSet bitset = solution.getVariableValue(0) ;
		solutionString = bitset.toString();
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
