package examination;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Student {
    private String name;
    private String klasa;
    
    //Student can do multiple exams and they will be added to the list
    //Aggregation relationship ('has a')
    public LinkedList<StudentExamined> results;
    //For JavaFx testing purposes
    public static Student currentStudent;
    
    private int examsDone=0;
    public Student(){
        
        results=new LinkedList<>();
    }
    //Overloaded constructor
    public Student(String name, String klasa){
        this.setName(name);
        this.setKlasa(klasa);
        results=new LinkedList<>();
    }
    
    

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKlasa() {
		return klasa;
	}

	public void setKlasa(String klasa) {
		this.klasa = klasa;
	}
	
	
    //Creates file with students exam, and shows correct answers

	public void writeToFile(Exam exam, boolean open) {
		try {
			int r=checkForExam(exam);
		      if(r<0) {
		    	  return;//stops the function if not found
		      }
		      FileWriter writer = new FileWriter(name+"-"+exam.getName()+".txt");
		      
		      int i=0;
		      for(Question q : exam.getQList()) {
		    	  writer.write(q.toString(123));
		    	  String tmp=results.get(r).getAnswers().get((i++));
		    	  if(tmp.isEmpty()||tmp.isBlank())
			    	  writer.write("\nYou didn't choose \n\n");
		    	  else
		    		  writer.write("\nYou chose "+ tmp+"\n\n");
				}
		      
		      writer.close();
		      if(open) {
		    	  ProcessBuilder pb = new ProcessBuilder("notepad.exe", name+"-"+exam.getName()+".txt");
			      pb.start();
		      }
		    	  
		    } 
		catch (IOException e) {
		    System.out.println("An IOException error occurred.");
		    e.printStackTrace();
		    }
		
	}
	//checks for an exam that has been done by the student through its name
	public int checkForExam(Exam e) {
		int i=0;
		for(StudentExamined b : results) {
			if(b.getExamName().equals(e.getName()))
				return i;
			i++;
		}
		return -1;
	}
	
	public int getExamsDone() {
		return examsDone;
	}

	public void setExamsDone(int examsDone) {
		this.examsDone = examsDone;
	}
}
