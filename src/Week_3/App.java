package Week_3;

import Week_5.Models.Task;
import Week_5.Managers.TaskManager;
import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import java.io.*;

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

    // CSV file
    private final File taskFile = new File("tasks.csv");

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

        // Connect ListView to task collection
        filteredTasks = new FilteredList<>(
                taskManager.getTasks(),
                task -> true
        );

        taskList.setItems(filteredTasks);

        // Create buttons
        Button addButton = new Button("Add Task");
        Button completeButton = new Button("Mark Complete / Active");
        Button removeButton = new Button("Remove Task");
        Button saveButton = new Button("Save Tasks");
        Button loadButton = new Button("Load Tasks");

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
                removeButton,
                saveButton,
                loadButton
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

        saveButton.setOnAction(
                event -> saveTasks()
        );

        loadButton.setOnAction(
                event -> loadTasks()
        );

        filterBox.setOnAction(
                event -> filterTasks()
        );

        taskInput.setOnAction(
                event -> addTask()
        );

        // Window settings
        Scene scene = new Scene(layout, 800, 450);

        stage.setTitle("Mini Task Manager");
        stage.setScene(scene);
        stage.show();
    }

    // Add task
    private void addTask() {

        String name = taskInput.getText().trim();

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

    // Save tasks to CSV file
    private void saveTasks() {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(taskFile))) {

            // Write CSV header
            writer.println("Name,Priority,Completed");

            // Write each task
            for (Task task : taskManager.getTasks()) {

                // Escape quotes in task names
                String escapedName = task.getName()
                        .replace("\"", "\"\"");

                writer.println(
                        "\"" + escapedName + "\"," +
                                task.getPriority() + "," +
                                task.isCompleted()
                );
            }

            showMessage("Tasks saved successfully!");

        } catch (IOException e) {

            showMessage(
                    "Error saving tasks: " + e.getMessage()
            );
        }
    }

    // Load tasks from CSV file
    private void loadTasks() {

        if (!taskFile.exists()) {
            showMessage("No saved tasks file found.");
            return;
        }

        // Temporary list prevents partial updates
        java.util.List<Task> loadedTasks =
                new java.util.ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(taskFile))) {

            String header = reader.readLine();

            if (!"Name,Priority,Completed".equals(header)) {
                throw new IOException("Invalid CSV header.");
            }

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                String[] data = parseCsvLine(line);

                if (data.length != 3) {
                    throw new IOException(
                            "Invalid data on line " + lineNumber
                    );
                }

                String name = data[0].trim();

                String priorityText = data[1].trim();
                String completedText = data[2].trim();

                if (name.isEmpty()) {
                    throw new IOException(
                            "Task name is empty on line " + lineNumber
                    );
                }

                if (!priorityText.equalsIgnoreCase("true") &&
                        !priorityText.equalsIgnoreCase("false")) {

                    throw new IOException(
                            "Invalid priority on line " + lineNumber
                    );
                }

                if (!completedText.equalsIgnoreCase("true") &&
                        !completedText.equalsIgnoreCase("false")) {

                    throw new IOException(
                            "Invalid completion status on line " + lineNumber
                    );
                }

                boolean priority =
                        Boolean.parseBoolean(priorityText);

                boolean completed =
                        Boolean.parseBoolean(completedText);

                // Create task
                Task task = new Task(name, priority);

                task.setCompleted(completed);

                loadedTasks.add(task);
            }

            // Replace existing tasks only after successful loading
            taskManager.getTasks().setAll(loadedTasks);

            // Refresh displayed list
            filterBox.setValue("All Tasks");
            filterTasks();
            taskList.refresh();

            showMessage("Tasks loaded successfully!");

        } catch (IOException e) {

            showMessage(
                    "Error loading tasks: " + e.getMessage()
            );
        }
    }

    // Parse a CSV line, including quoted task names
    private String[] parseCsvLine(String line)
            throws IOException {

        java.util.List<String> values =
                new java.util.ArrayList<>();

        StringBuilder value = new StringBuilder();

        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {

            char current = line.charAt(i);

            if (current == '"') {

                if (insideQuotes &&
                        i + 1 < line.length() &&
                        line.charAt(i + 1) == '"') {

                    value.append('"');
                    i++;

                } else {
                    insideQuotes = !insideQuotes;
                }

            } else if (current == ',' && !insideQuotes) {

                values.add(value.toString());
                value.setLength(0);

            } else {
                value.append(current);
            }
        }

        if (insideQuotes) {
            throw new IOException("Unclosed quotation mark.");
        }

        values.add(value.toString());

        return values.toArray(new String[0]);
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
