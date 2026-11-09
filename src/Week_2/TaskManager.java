package Week_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskManager {

    private ObservableList<Task> tasks;

    public TaskManager() {
        tasks = FXCollections.observableArrayList();
    }

    // Add new task
    public void addTask(String name, boolean priority) {
        Task task = new Task(name, priority);
        tasks.add(task);
    }

    // Remove task
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void toggleCompleted(Task task) {
        task.setCompleted(!task.isCompleted());
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }
}
