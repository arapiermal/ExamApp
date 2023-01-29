package examination;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

//in order to access the FXML based app's objects and events (when buttons are clicked for ex.) 
public class AppController {
	public int step=0;
	public boolean hasFinished=false;
	public static int howManyExams=-1;
	@FXML
	private TextField answerBox;

	@FXML
	private TextField klasaBox;

	@FXML
	private Label mainText;

	@FXML
	private TextField nameBox;

	@FXML
	private Label resultBox;
	   
	@FXML
	private Label studentNameAndKlasa;
	   
	@FXML
	private Font x1;

	@FXML
	private Color x2;

	@FXML
	private Font x3;

	@FXML
	private Color x4;
    
    @FXML
    public void finishExam(ActionEvent event) {
    	//sees if any exam has initialized, or it has finished, if so the method stops
    	if(howManyExams<0||hasFinished)
    		return;
    	//gets the answer of the final question (since Next doesn't work anymore)
    	String temp=answerBox.getText();
    	answerBox.setText("");
   		Student.currentStudent.results.get(howManyExams).setAnswers(step,temp);
   		//checks the answers
    	for(int i=0;i<Student.currentStudent.results.get(howManyExams).getAnswers().size();i++) {
    		temp=Student.currentStudent.results.get(howManyExams).getAnswers().get(i);
        	if(Exam.currentExam.getQList().get(i) instanceof AnswerQuestion) {
        		AnswerQuestion q = (AnswerQuestion) Exam.currentExam.getQList().get(i);
        		if(temp.equalsIgnoreCase(q.getCorrectAnswer())){
        			Student.currentStudent.results.get(howManyExams).addPoints(q.getPoints());
        		}
        	}
        	else if(Exam.currentExam.getQList().get(step) instanceof MultipleChoiceQuestion) {
        		MultipleChoiceQuestion q = (MultipleChoiceQuestion) Exam.currentExam.getQList().get(i);
        		if(temp.equalsIgnoreCase(q.getCorrectChoice(1))){
        			Student.currentStudent.results.get(howManyExams).addPoints(q.getPoints());
        		}
        	}
    	}
    	resultBox.setText(Student.currentStudent.results.get(howManyExams).toString());
    	hasFinished=true;
    	
    }

    @FXML
    public void goBack(ActionEvent event) {
    	if(step<=0||howManyExams<0||hasFinished)
    		return;
    	
    	step--;
        mainText.setText((step+1)+"/"+Exam.currentExam.getQList().size()+"\n"+Exam.currentExam.getQList().get(step).toString());
        //loads last answer
    	answerBox.setText(Student.currentStudent.results.get(howManyExams).getAnswers().get(step));
    	
    }

    @FXML
    public void goNext(ActionEvent event) {
    	if(step>=Exam.currentExam.getQList().size()-1||howManyExams<0||hasFinished)
    		return;
    	String temp=answerBox.getText();
    	answerBox.setText(Student.currentStudent.results.get(howManyExams).getAnswers().get(step+1));
   		Student.currentStudent.results.get(howManyExams).setAnswers(step,temp);
    	step++;
    	mainText.setText((step+1)+"/"+Exam.currentExam.getQList().size()+"\n"+Exam.currentExam.getQList().get(step).toString());
    	
    }
    //
    //Create a new text file with a template
    @FXML
    public void newTxtFile(ActionEvent event) {
    	
    	TextInputDialog t=new TextInputDialog("Name for the text file");
    	t.setTitle("Template maker");
    	t.setHeaderText("Give a name to the file for the template");
    	Optional<String> result = t.showAndWait();
    	if (result.isPresent()) {
    	    //Ok was pressed
        	Main.createTemplateFile(t.getResult());
    	}
    	
    }

    @FXML
    public void openTxtFile(ActionEvent event) {
    	//

		if(!Main.popUpFileChooser())
			return; 
		//stops the rest of the method from executing if it throws an error
		//had do to it because if the fileChooser gets closed without opening anything, it throws errors
		hasFinished=false;
		step=0;
		mainText.setText((step+1)+"/"+Exam.currentExam.getQList().size()+"\n"+Exam.currentExam.getQList().get(step).toString());
    	//
		Student.currentStudent.results.add(new StudentExamined(Exam.currentExam.getName(), Exam.currentExam.getMaxPoints()));
		howManyExams++;
		//for ease empty answers are created
		for(int i=0;i<Exam.currentExam.getQList().size();i++) {
			Student.currentStudent.results.get(howManyExams).getAnswers().add("");
		}

    }
    

    @FXML
    public void submitStudent(ActionEvent event) {
    	Student.currentStudent.setName(nameBox.getText());
    	Student.currentStudent.setKlasa(klasaBox.getText());
    	studentNameAndKlasa.setText("Student\n"+Student.currentStudent.getName()+"\nClass\n"+Student.currentStudent.getKlasa());
    }
    
    //A file named as StudentName-ExamName.txt is created with the correct and chosen answers
    //Then using processbuilder it is opened
    @FXML
    void printResultsToFile(ActionEvent event) {
    	if(!hasFinished)
    		return;
    	Student.currentStudent.writeToFile(Exam.currentExam,true);
    	
    }
    //pops up an alert with information if you click about
    @FXML
    void showInfoAlert(ActionEvent event) {
    	Alert alert=new Alert(AlertType.INFORMATION,"Exam");
    	alert.setTitle("About");
    	alert.setHeaderText("Info");
    	alert.showAndWait();
    	
    }
    

}
