package Week_2;

import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.stage.Stage;

// GitHub Link: https://github.com/PrstnLee/CS-372

public class App extends Application {

    // Data manager
    private TaskManager taskManager;

    // Interface controls
    private TextField taskInput;
    private CheckBox priorityCheckBox;
    private ComboBox<String> filterBox;
    private ListView<Task> taskList;

    private FilteredList<Task> filteredTasks;

    @Override
    public void start(Stage stage) {

        taskManager = new TaskManager();

        taskInput = new TextField();
        taskInput.setPromptText("Enter a new task...");

        priorityCheckBox = new CheckBox("High Priority");


        // Create buttons
        Button addButton = new Button("Add Task");
        Button completeButton = new Button("Mark Complete / Active");
        Button removeButton = new Button("Remove Task");

        // Add section
        Label title = new Label("My Task Manager");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label inputLabel = new Label("New Task:");

        HBox inputRow = new HBox(10);
        inputRow.getChildren().addAll(
                taskInput,
                priorityCheckBox,
                addButton
        );



        // Main layout
        VBox layout = new VBox(15);

        layout.setPadding(new Insets(20));

        layout.getChildren().addAll(
                title,
                inputLabel,
                inputRow
        );

        // Window settings
        Scene scene = new Scene(layout, 650, 450);

        stage.setTitle("Mini Task Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
