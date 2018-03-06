package FrequentQuestions;

public class FrequentQuestions {
	
	private String number;
	private String question;
	private String answer;

	public FrequentQuestions(String number, String question, String answer) {
		this.setNumber(number);
		this.setQuestion(question);
		this.setAnswer(answer);
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Override
	public String toString() {
		return "[" + number + ", " + question + ", " + answer + "]";
	}

}
