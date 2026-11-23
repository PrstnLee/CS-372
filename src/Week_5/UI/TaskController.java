package Week_5.UI;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;

import Week_5.Managers.TaskManager;
import Week_5.Models.Task;
import Week_5.Storage.TaskFileHandler;
import Week_5.UI.TaskView;

import java.io.IOException;
import java.util.List;

public class TaskController {

    private TaskManager taskManager;
    private TaskView taskView;
    private TaskFileHandler fileHandler;

    private FilteredList<Task> filteredTasks;

    public TaskController(
            TaskManager taskManager,
            TaskView taskView,
            TaskFileHandler fileHandler) {

        this.taskManager = taskManager;
        this.taskView = taskView;
        this.fileHandler = fileHandler;

        // Connect task collection to filtered list
        filteredTasks = new FilteredList<>(
                taskManager.getTasks(),
                task -> true
        );

        taskView.setFilteredTasks(filteredTasks);

        // Connect buttons to methods
        taskView.getAddButton().setOnAction(
                event -> addTask()
        );

        taskView.getCompleteButton().setOnAction(
                event -> markTaskCompleted()
        );

        taskView.getRemoveButton().setOnAction(
                event -> removeTask()
        );

        taskView.getSaveButton().setOnAction(
                event -> saveTasks()
        );

        taskView.getLoadButton().setOnAction(
                event -> loadTasks()
        );

        taskView.getFilterBox().setOnAction(
                event -> filterTasks()
        );

        taskView.getTaskInput().setOnAction(
                event -> addTask()
        );
    }

    // Add task
    private void addTask() {

        String name = taskView.getTaskInput()
                .getText().trim();

        boolean priority = taskView
                .getPriorityCheckBox().isSelected();

        if (name.isEmpty()) {
            showMessage("Please enter a task name.");
            return;
        }

        taskManager.addTask(name, priority);

        taskView.getTaskInput().clear();
        taskView.getPriorityCheckBox().setSelected(false);

        taskView.getFilterBox().setValue("All Tasks");

        filterTasks();
    }

    // Toggle completion
    private void markTaskCompleted() {

        Task selectedTask = taskView.getTaskList()
                .getSelectionModel()
                .getSelectedItem();

        if (selectedTask == null) {
            showMessage("Please select a task first.");
            return;
        }

        taskManager.toggleCompleted(selectedTask);

        taskView.getTaskList().refresh();

        filterTasks();
    }

    // Remove task
    private void removeTask() {

        Task selectedTask = taskView.getTaskList()
                .getSelectionModel()
                .getSelectedItem();

        if (selectedTask == null) {
            showMessage("Please select a task to remove.");
            return;
        }

        taskManager.removeTask(selectedTask);
    }

    // Save tasks
    private void saveTasks() {

        try {

            fileHandler.saveTasks(taskManager.getTasks());

            showMessage("Tasks saved successfully!");

        } catch (IOException e) {

            showMessage(
                    "Error saving tasks: " + e.getMessage()
            );
        }
    }

    // Load tasks
    private void loadTasks() {

        try {

            List<Task> loadedTasks = fileHandler.loadTasks();

            // Replace existing tasks after successful loading
            taskManager.getTasks().setAll(loadedTasks);

            taskView.getFilterBox().setValue("All Tasks");

            filterTasks();

            taskView.getTaskList().refresh();

            showMessage("Tasks loaded successfully!");

        } catch (IOException e) {

            showMessage(
                    "Error loading tasks: " + e.getMessage()
            );
        }
    }

    // Filter tasks
    private void filterTasks() {

        String filter = taskView.getFilterBox().getValue();

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

    // Display messages
    private void showMessage(String message) {

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle("Task Manager");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
