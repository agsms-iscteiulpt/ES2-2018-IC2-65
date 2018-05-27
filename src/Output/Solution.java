package Output;

public class Solution {
	
	private String algorithm;
	private String criteria1;
	private String criteria2;
	
	/**
	 * 
	 * @param algorithm
	 * @param criteria1
	 * @param criteria2
	 */
	public Solution(String algorithm, String criteria1, String criteria2) {
		super();
		this.algorithm = algorithm;
		this.criteria1 = criteria1;
		this.criteria2 = criteria2;
	}
	
	/**
	 * @return the algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm the algorithm to set
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * @return the criteria1
	 */
	public String getCriteria1() {
		return criteria1;
	}

	/**
	 * @param criteria1 the criteria1 to set
	 */
	public void setCriteria1(String criteria1) {
		this.criteria1 = criteria1;
	}

	/**
	 * @return the criteria2
	 */
	public String getCriteria2() {
		return criteria2;
	}

	/**
	 * @param criteria2 the criteria2 to set
	 */
	public void setCriteria2(String criteria2) {
		this.criteria2 = criteria2;
	}
	
	@Override
	public String toString() {
		return "Solution [algorithm=" + algorithm + ", criteria1=" + criteria1
				+ ", criteria2=" + criteria2 + "]";
	}
}


