package examination;

public class AnswerQuestion extends Question {
    private String correctAnswer;
    
    public AnswerQuestion(String question,int points){
        super(question,points);
    }
    //Overloaded Constructor
    public AnswerQuestion(String question,int points,String correctAnswer){
        super(question,points);
        setCorrectAnswer(correctAnswer);
    }
    // lower case or upper case doesn't matter
    public boolean checkAnswer(String s){
        return correctAnswer.equalsIgnoreCase(s);
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    //in order to get the question and points for use, overrides the default toString()
    public String toString() {
    	return "Question: \n"+getQuestion()+"\nPoints: "+getPoints();
    }
    
    //give answers through using a special key
	@Override
    public String toString(int getAnswer) {
    	
    	if(getAnswer==Login.ADMIN_KEY)
    		return "Question: \n"+getQuestion()+"\nPoints: "+getPoints()+"\nAnswer: "+correctAnswer;
    	return "You don't have access for the answers!";
    }
}
