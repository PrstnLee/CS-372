package Week_5.UI;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import Week_5.Models.Task;

public class TaskView {

    private VBox layout;

    private TextField taskInput;
    private CheckBox priorityCheckBox;
    private ComboBox<String> filterBox;
    private ListView<Task> taskList;

    private Button addButton;
    private Button completeButton;
    private Button removeButton;
    private Button saveButton;
    private Button loadButton;

    private FilteredList<Task> filteredTasks;

    public TaskView() {

        // Input controls
        taskInput = new TextField();
        taskInput.setPromptText("Enter a new task...");

        priorityCheckBox = new CheckBox("High Priority");

        // Filter controls
        filterBox = new ComboBox<>();
        filterBox.getItems().addAll(
                "All Tasks",
                "Active",
                "Completed"
        );
        filterBox.setValue("All Tasks");

        // Task list
        taskList = new ListView<>();

        // Buttons
        addButton = new Button("Add Task");
        completeButton = new Button("Mark Complete / Active");
        removeButton = new Button("Remove Task");
        saveButton = new Button("Save Tasks");
        loadButton = new Button("Load Tasks");

        // Title
        Label title = new Label("My Task Manager");
        title.setStyle(
                "-fx-font-size: 24px; -fx-font-weight: bold;"
        );

        Label inputLabel = new Label("New Task:");

        HBox inputRow = new HBox(
                10,
                taskInput,
                priorityCheckBox,
                addButton
        );

        Label filterLabel = new Label("Filter Tasks:");

        HBox filterRow = new HBox(
                10,
                filterLabel,
                filterBox
        );

        HBox buttonRow = new HBox(
                10,
                completeButton,
                removeButton,
                saveButton,
                loadButton
        );

        // Main layout
        layout = new VBox(15);

        layout.setPadding(new Insets(20));

        layout.getChildren().addAll(
                title,
                inputLabel,
                inputRow,
                filterRow,
                taskList,
                buttonRow
        );

        // Allow task list to grow
        VBox.setVgrow(taskList, Priority.ALWAYS);
    }

    // Getters for interface components

    public VBox getLayout() {
        return layout;
    }

    public TextField getTaskInput() {
        return taskInput;
    }

    public CheckBox getPriorityCheckBox() {
        return priorityCheckBox;
    }

    public ComboBox<String> getFilterBox() {
        return filterBox;
    }

    public ListView<Task> getTaskList() {
        return taskList;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getCompleteButton() {
        return completeButton;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getLoadButton() {
        return loadButton;
    }

    public void setFilteredTasks(
            FilteredList<Task> tasks
    ) {
        filteredTasks = tasks;
        taskList.setItems(filteredTasks);
    }

    public FilteredList<Task> getFilteredTasks() {
        return filteredTasks;
    }
}

