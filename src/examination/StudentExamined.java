package examination;


import java.util.LinkedList;

public class StudentExamined {
	//storing the results and answers of the student taking the exam
	
	private String examName;
	private int maxPoints;
	private int points;
	private LinkedList<String> answers;
	
	//gives grade from 0.0 to 10.0 rounded up to 2 digits
	public float getGrade(){
    	
        return (float) Math.round((float) points/maxPoints *1000)/100;
    }
	
	public StudentExamined(String examName,int maxPoints) {
		this.examName=examName;
		this.maxPoints=maxPoints;
		answers = new LinkedList<>();
	}
	
	//checks whether the student passed
    public boolean hasPassed(){
        return getGrade()>=4.5;
    }
    //relating to test
    public void addPoints(int p){
        points+=p;
    }

	public int getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public LinkedList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(LinkedList<String> answers) {
		this.answers = answers;
	}
	//set specific answer
	public void setAnswers(int i, String s) {
		this.answers.set(i, s);
	}
	public void addAnswers(String s) {
		this.answers.add(s);
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	
	public String toString() {
		return examName+"\nPoints: "+points+"/"+maxPoints+"\nGrade: "+getGrade()+"\nHas passed: "+hasPassed();
	}
	
}
