package examination;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleTesting {
	private static boolean hasExam=false;
	private static Exam ex=new Exam();
	private static Student student;
	private static Scanner ans=new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Welcome");
		student=studentData();
		boolean b=false;
		//loop for the program until it ends
		while(!b) {
			menu();
			int s=scanInt();
			switch(s) {
				case 1:
					System.out.println("Give file that has the exam with the .txt");
					String p=ans.nextLine();
					
					if(Files.exists(Paths.get(p))) {
						ex.loadFromFile(p);
						hasExam=true;
					}
					else
						System.out.println("Couldn't get file");
					break;
				case 2:
					if(loginAdmin()) {
						makeExam();
						hasExam=true;
					}
					
					break;
				case 3:
					if(hasExam)
						ask(ex,student);
					break;
				case 4:
					if(student.getExamsDone()>0) {
						printExam(ex,student);
						student.writeToFile(ex,true);
					}
					break;
				case 9:
					System.out.println("Exiting program");
					System.exit(0);
				default:System.out.println("No such choice available");
			}
			
			
			
		}
	}
	//In order to create a new exam, a login is required
	private static boolean loginAdmin() {
		System.out.println("You need credentials");
		System.out.print("Input username: ");
		boolean correctUser=ans.nextLine().equals(Login.ADMIN_USERNAME);
		System.out.print("Input password: ");
		boolean correctPassword=ans.nextLine().equals(Login.ADMIN_PASSWORD);
		if(correctUser&&correctPassword)
			return true;
		return false;
	}
	//The questioning of the student
	public static void ask(Exam e,Student s) {
		//
		StudentExamined a=new StudentExamined(e.getName(),e.getMaxPoints());
		
		System.out.println("Exam - "+e.getName());
		
		String temp;
		for(Question q : e.getQList()) {
			System.out.println(q.toString());
			if(q instanceof AnswerQuestion) {
				System.out.println("Give answer in word/s");
				temp=ans.next();
				//
				ans.nextLine();
				//
				a.addAnswers(temp);
				if(temp.equalsIgnoreCase(((AnswerQuestion) q).getCorrectAnswer()))
					a.addPoints(q.getPoints());
			}
			else if(q instanceof MultipleChoiceQuestion) {
				System.out.println("Give answer with one of the letters (a,b,c,etc.)");
				temp=ans.next();
				//
				ans.nextLine();
				//
				a.addAnswers(temp);
				if(temp.equalsIgnoreCase(((MultipleChoiceQuestion) q).getCorrectChoice(1)))
					a.addPoints(q.getPoints());
			}
		}
		//adding the results to the record of the student
		s.results.add(a);
		int nr=s.results.size()-1;
		System.out.println(s.results.get(nr).getPoints()+" pike nga "+s.results.get(nr).getMaxPoints());
		System.out.println("Nota "+s.results.get(nr).getGrade());
		s.setExamsDone(s.getExamsDone()+1);

	}

	public static void printExam(Exam e,Student s){
		//iterate to find examName==exam;
		int r=s.checkForExam(e); //look in the records of the results of the student
		if(r>=0) {
			int i=0;
			
			for(Question q : e.getQList()) {
				System.out.println(q.toString(123)+"\nYou chose "+ s.results.get(r).getAnswers().get((i++)));
			}
		}
		else
			System.out.println("Student "+s.getName()+" hasn't done exam "+e.getName());
	}
	
	public static Student studentData() {
		System.out.print("Emri dhe mbiemri: ");
		String name=ans.nextLine();
		System.out.print("Klasa: ");
		String klasa=ans.nextLine();

		return new Student(name,klasa);
	}
	//create an exam from scratch more simply than with a template
	public static void makeExam() {
		
		ex.eraseAllQuestions();
		System.out.println("You are making an exam which then will be put into a txt file");
		System.out.println("Exam name:");
		String get=ans.next();
		//
		ans.nextLine();
		//
		ex.setName(get);
		while(!get.equals("end")) {
			//
			System.out.print("Type 'end' to end\nAnswer question (type AQ)\nMultiple choice question (type MC)\nAdd: ");
			get=ans.next();
			//
			ans.nextLine();
			//
			if(get.equalsIgnoreCase("end"))
				break;
			if(get.equalsIgnoreCase("MC")) {
				System.out.println("The question of the MultipleChoiceQuestion: ");
				get=ans.nextLine();
				System.out.println("How many points does this question have:");
				MultipleChoiceQuestion q=new MultipleChoiceQuestion(get,scanInt());
				System.out.println("How many question would you like to add? (from one to five)");
				
				int a=scanInt();
				if(a>5||a<1) {
					System.out.println("Setting choices to four");
					a=4;
				}
				for(int i=0;i<a;i++) {
					System.out.println("Add the number "+(i+1)+" choice");
					get=ans.nextLine();
					q.getChoices().add(get);
				}
				System.out.println("Which one is correct? (a number from 1st, up to the last)");
				int b=scanInt()-1;
				if(b>a-1||b<0) {
					System.out.println("Error, setting first choice as correct.\n(You can always edit the file which will be generated and load it)");
					b=0;
				}
				q.setCorrectChoice(b);
				ex.addQuestion(q);
					
			}
			else if(get.equalsIgnoreCase("AQ")) {
				System.out.println("The question of the AnswerQuestion: ");
				get=ans.nextLine();
				System.out.println("How many points does this question have:");
				AnswerQuestion q=new AnswerQuestion(get,scanInt());
				System.out.println("Which is the correct answer?");
				get=ans.next();
				//
				ans.nextLine();
				//
				q.setCorrectAnswer(get);
				ex.addQuestion(q);
			}
			else {
				System.out.println("No such question type, you can end the exam by typing 'end'.");
			}
		}
		ex.setMaxPoints();
		ex.saveToFile(ex.getName());
	}
	//returns a scanned integer
	public static int scanInt() {
		boolean b=false;
		int g;
		while(!b) {
			try {
				g=ans.nextInt();
				//throw away the \n not consumed by nextInt() //troubleshoot from Internet
				ans.nextLine();

				if(g<0)
					return 0;
				b=true;
				return g;
			}
			catch(InputMismatchException e) {
				System.out.println("Error, we need an integer.\nTry again: ");
				ans.next();
			}
		}
		return 0;
	}
	//Text menu
	public static void menu() {
		System.out.println("Menu\n1) Load exam from file\n2) Create exam");
		if(hasExam)
			System.out.println("3) Start exam: "+ex.getName());
		if(student.getExamsDone()>0)
			System.out.println("4) Print exam results with answers and also in .txt");
		System.out.println("9) Exit program");
		System.out.print("Input: ");
	}
	
}
	
	
