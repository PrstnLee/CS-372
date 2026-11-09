package Week_2;

import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.stage.Stage;

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

        filterBox = new ComboBox<>();
        filterBox.getItems().addAll(
                "All Tasks", "Active", "Completed"
        );
        filterBox.setValue("All Tasks");

        taskList = new ListView<>();

        filteredTasks = new FilteredList<>(
                taskManager.getTasks(),
                task -> true
        );

        taskList.setItems(filteredTasks);

        // Main layout
        VBox layout = new VBox(15);

        layout.setPadding(new Insets(20));

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
