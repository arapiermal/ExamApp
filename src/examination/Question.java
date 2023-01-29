package examination;

public class Question {
	//Superclass
	
    private String question;
    private int points;
    
    public Question(String question,int points){
        this.question=question;
        this.points=points;
    }
    
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    
    public String toString() {
    	return "Question: \n"+question+"\nPoints: "+points;
    }
    //for easier iteration, @override-d in the other subclasses
    public String toString(int getAnswer) {
    	return "Question: \n"+getQuestion()+"\nPoints: "+getPoints()+"\n Nothing here";
    }
}
