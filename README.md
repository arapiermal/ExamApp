# ExamApp
You can do exams and get the results

Up to now, 2 ways for the app:
Console and JavaFX (GUI) version

ConsoleTesting.java
- Load exams
- *Create exams* (easier than with the JavaFX version)
- Runs exams
- Saves exam results into a file and shows corrects answers


Main.java
- GUI
- Loads exams using a FileChooser (GUI)
- Creates a .txt Exam template if you click File and then New, which is automatically opened through ProcessBuilder
- Runs exams (can go back and forwards with the questions)
- Finish button finishes the exam and shows the results on the right
- Print file creates a file with the exam using the given and correct answers

Question superclass with AnswerQuestion and MultipleChoiceQuestion subclasses

Exam class with an aggregate ('has a' relationship) Question LinkedList, polymorphism is used

Student can take multiple exams, StudentExamined class as an aggregate, again with LinkedList

ConsoleTesting.java has a menu which is repeated until the user decides to quit.

Main.java loads a FXML file build with SceneBuilder, and a Controller which can get and set information, input and events from the app live, making it interactive.
