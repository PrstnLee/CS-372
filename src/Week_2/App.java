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

    // Filtered view of tasks
    private FilteredList<Task> filteredTasks;

    @Override
    public void start(Stage stage) {

        taskManager = new TaskManager();

        // Create interface controls
        taskInput = new TextField();
        taskInput.setPromptText("Enter a new task...");

        priorityCheckBox = new CheckBox("High Priority");

        filterBox = new ComboBox<>();
        filterBox.getItems().addAll(
                "All Tasks", "Active", "Completed"
        );
        filterBox.setValue("All Tasks");

        taskList = new ListView<>();

        // Connect ListView to the task collection
        filteredTasks = new FilteredList<>(
                taskManager.getTasks(),
                task -> true
        );

        taskList.setItems(filteredTasks);

        // Create buttons
        Button addButton = new Button("Add Task");
        Button completeButton = new Button("Mark Complete / Active");
        Button removeButton = new Button("Remove Task");

        // Add task input section
        Label title = new Label("My Task Manager");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label inputLabel = new Label("New Task:");

        HBox inputRow = new HBox(10);
        inputRow.getChildren().addAll(
                taskInput,
                priorityCheckBox,
                addButton
        );

        // Filter section
        Label filterLabel = new Label("Filter Tasks:");

        HBox filterRow = new HBox(10);
        filterRow.getChildren().addAll(
                filterLabel,
                filterBox
        );

        // Action buttons
        HBox buttonRow = new HBox(10);
        buttonRow.getChildren().addAll(
                completeButton,
                removeButton
        );

        // Main layout
        VBox layout = new VBox(15);

        layout.setPadding(new Insets(20));

        layout.getChildren().addAll(
                title,
                inputLabel,
                inputRow,
                filterRow,
                taskList,
                buttonRow
        );

        // Button event handlers
        addButton.setOnAction(event -> addTask());

        completeButton.setOnAction(
                event -> markTaskCompleted()
        );

        removeButton.setOnAction(
                event -> removeTask()
        );

        filterBox.setOnAction(
                event -> filterTasks()
        );

        taskInput.setOnAction(
                event -> addTask()
        );

        // Window settings
        Scene scene = new Scene(layout, 650, 450);

        stage.setTitle("Mini Task Manager");
        stage.setScene(scene);
        stage.show();
    }

    // Add task
    private void addTask() {

        String name = taskInput.getText().trim();

        // Get value from checkbox
        boolean priority = priorityCheckBox.isSelected();

        if (name.isEmpty()) {
            showMessage("Please enter a task name.");
            return;
        }

        taskManager.addTask(name, priority);

        taskInput.clear();
        priorityCheckBox.setSelected(false);

        filterBox.setValue("All Tasks");
        filterTasks();
    }

    // Mark task completed
    private void markTaskCompleted() {

        Task selectedTask = taskList.getSelectionModel()
                .getSelectedItem();

        if (selectedTask == null) {
            showMessage("Please select a task first.");
            return;
        }

        taskManager.toggleCompleted(selectedTask);

        taskList.refresh();

        filterTasks();
    }

    // Remove task
    private void removeTask() {

        Task selectedTask = taskList.getSelectionModel()
                .getSelectedItem();

        if (selectedTask == null) {
            showMessage("Please select a task to remove.");
            return;
        }

        taskManager.removeTask(selectedTask);
    }

    // Filter completed tasks
    private void filterTasks() {

        String filter = filterBox.getValue();

        filteredTasks.setPredicate(task -> {

            if (filter == null || filter.equals("All Tasks")) {
                return true;
            }

            if (filter.equals("Active")) {
                return !task.isCompleted();
            }

            if (filter.equals("Completed")) {
                return task.isCompleted();
            }

            return true;
        });
    }

    // Display message to user
    private void showMessage(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Task Manager");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
