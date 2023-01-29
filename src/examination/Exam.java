package examination;


import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class Exam {
	
	private String name;
	private int maxPoints; //added by adding more questions
	//Aggregation ('has a' relationship)
	private LinkedList<Question> qList; //list of questions
	//testing purposes for JavaFx
	public static Exam currentExam;
	
	public Exam(){
		qList=new LinkedList<>();
	}
	//making methods which utilize already given methods because qList is declared as private (for ease)
	public void addQuestion(Question e){
		qList.add(e);
	}
	public void removeQuestion(Question e){
		qList.remove(e);
	}
	public void eraseAllQuestions(){
		qList.clear();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Question> getQList() {
		return qList;
	}
	public int getMaxPoints() {
		return maxPoints;
	}
	//MaxPoints is dependent on the total of the points of each question, no input required
	public void setMaxPoints() {
		maxPoints=0;
		for(Question q : qList)
			this.maxPoints += q.getPoints();
	}
	
	//load and save a file through a text structure
	//QA for AnswerQuestion and MC for MultipleChoiceQuestion, more can be added
	
	public void loadFromFile(String textName) {
		Scanner scan;
		String type;
		try{
			scan=new Scanner(Paths.get(textName));
			name=scan.nextLine();
			while(scan.hasNext()) {
				type=scan.next();
				scan.nextLine();
				if(type.equals("QA")) {
					String q=scan.nextLine();
					int p=scan.nextInt();
					scan.nextLine();
					Question q1=new AnswerQuestion(q,p,scan.nextLine());
					qList.add(q1);
				}
				else if(type.equals("MC")) {
					int count=0;
					MultipleChoiceQuestion q1=new MultipleChoiceQuestion(scan.nextLine(),scan.nextInt());
					scan.nextLine();
					
					
					while(scan.hasNext("Choice")) {
						scan.next();
						if(scan.hasNext("Correct"))
							q1.setCorrectChoice(count);
						scan.nextLine();
						q1.getChoices().add(scan.nextLine());
						count++;
					}
						
					qList.add(q1);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		setMaxPoints();
		
	}
	//saves the exam object as a format for future loading
	public void saveToFile(String txtName) {
		try {
			FileWriter writer = new FileWriter(txtName+".txt");
			writer.write(name+"\n");
			for(Question q : qList) {
				if(q instanceof AnswerQuestion) {
					writer.write("QA\n"+q.getQuestion()+"\n"+q.getPoints()+"\n");
					writer.write(((AnswerQuestion) q).getCorrectAnswer()+"\n");
				}
				else if(q instanceof MultipleChoiceQuestion) {
					writer.write("MC\n"+q.getQuestion()+"\n"+q.getPoints());
					for(int i=0;i<((MultipleChoiceQuestion) q).getChoices().size();i++) {
						writer.write("\nChoice");
						if(i == ((MultipleChoiceQuestion) q).getCorrectChoice())
							writer.write(" Correct\n");
						else
							writer.write("\n");
						writer.write(((MultipleChoiceQuestion) q).getChoices().get(i));
					}
					writer.write("\n");
				}
			}
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//Basic template that is used for the creation of a '.txt' template
	public static String fileTemplate() {
		return "ExamNameTemplate\n"
				+ "QA\n"
				+ "Here is a question with a single answer (declared up as QA (Question Answer)) and right below are the points\n"
				+ "3\n"
				+ "HereIsTheAnswer\n"
				+ "MC\n"
				+ "Here is a multiple choice question\n"
				+ "5\n"
				+ "Choice\n"
				+ "This is the first choice\n"
				+ "Choice Correct\n"
				+ "This is the second choice which is marked as the correct one\n"
				+ "Choice\n"
				+ "This is the third choice\n"
				+ "Choice\n"
				+ "This is the fourth choice, you can make up to 5 choices for an MC type question\n"
				+ "Choice\n"
				+ "This is the fifth choice. You can make more questions using these formats.";
	}
}