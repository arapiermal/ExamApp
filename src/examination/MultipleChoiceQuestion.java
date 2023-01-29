package examination;

import java.util.ArrayList;

public class MultipleChoiceQuestion extends Question{
	//subclass of Question, used for questions which have a few options as potential answers
	private ArrayList<String> choices=new ArrayList<>();
	private int correctChoice;
	public MultipleChoiceQuestion(String question,int points) {
        super(question,points);
	}
	//Overloaded constructor
	public MultipleChoiceQuestion(String question,int points,int correct) {
        super(question,points);
		setCorrectChoice(correct);
	}
	
	public void setChoices(String s, int i) {
		this.choices.set(i, s);
	}
	
	public String toString() {
		String a="";
		char c='a';
		for(int i=0;i<choices.size();i++) {
			a+=(c+" - "+choices.get(i)+"\n");
			c++;
		}
    	return "Question: \n"+getQuestion()+"\nPoints: "+getPoints()+"\n"+a;
    }
	//give answers through using a special key
	@Override
	public String toString(int getAnswer) {
		if(getAnswer==Login.ADMIN_KEY) {
			String a="";
			char c='a';
			for(int i=0;i<choices.size();i++) {
				a+=(c+" - "+choices.get(i));
				if(i==correctChoice)
					a+=" (This is the correct answer.)";
				a+="\n";
				c++;
			}
    		return "Question: \n"+getQuestion()+"\nPoints: "+getPoints()+"\n"+a;
		}
    	return "You don't have access for the answers!";
    }
	public ArrayList<String> getChoices() {
		return choices;
	}
	public void setChoices(ArrayList<String> choices) {
		this.choices = choices;
	}
	public int getCorrectChoice() {
		return correctChoice;
	}
	//get a letter instead of a number, overloaded method
	public String getCorrectChoice(int i) {
		return Character.toString('a'+correctChoice);
	}
	public void setCorrectChoice(int correctChoice) {
		this.correctChoice = correctChoice;
	}

	
}
