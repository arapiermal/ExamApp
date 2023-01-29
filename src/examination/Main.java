package examination;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Using SceneBuilder which produces FXML
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("application.fxml"));
			//After the FXML loads, the controller is injected and then the whole thing is loaded
			loader.setController(new AppController());
			Parent root=loader.load();
			 
			Scene scene = new Scene(root);

			primaryStage.setTitle("Exam");
			primaryStage.setScene(scene);
			primaryStage.show();
			//testing
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		//start a student and exam instance
		Student.currentStudent=new Student("","");//
		Exam.currentExam=new Exam();
		//the app pops up
		launch(args);
	}
	//pops up a window to choose the file
	public static boolean popUpFileChooser() {
		try {
		//getting directory/making it for an initial directory for the fileChooser
		File recordsDir = new File(System.getProperty("user.home"), "eclipse-workspace");
		if (! recordsDir.exists()) {
		    recordsDir.mkdirs();
		}
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setInitialDirectory(recordsDir);
		fileChooser.setTitle("Open the '.txt' file containing an exam");
		//only .txt files can be chosen
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
			
		
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		//Empty exam if it's filled with questions
		if(AppController.howManyExams>=0)
			Exam.currentExam.eraseAllQuestions();
		//Exam is loaded using a method that's also used in the console testing of the app
		Exam.currentExam.loadFromFile(selectedFile.getAbsolutePath());
		}
		catch(Exception e) {
			return false;
		}
		return true;
		//try/catch, turned from method return from void into boolean to show success if true
	}
	
	public static void createTemplateFile(String name) {
		try {
		
		      File myObj = new File(name+".txt");
		      
		      if (myObj.createNewFile()) {
		    	  //File is created
		    	  FileWriter myWriter = new FileWriter(name+".txt");
		          myWriter.write(Exam.fileTemplate());
		          myWriter.close();
		          ProcessBuilder pb = new ProcessBuilder("notepad.exe", name+".txt");
		          pb.start();
		      } else {
		    	  //File already exists
		    	  ProcessBuilder pb = new ProcessBuilder("notepad.exe", name+".txt");
		          pb.start();
		      }
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	}
}
