package Week_2;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    List<Task> tasks = new ArrayList<Task>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void filterPriority(List<Task> tasks) {
        System.out.println("High Priority Tasks:");

        for (Task task : tasks) {
            if (task.priority) {
                System.out.println(task.name);
            }
        }
    }

    public void filterCompleted(List<Task> tasks) {
        System.out.println("Completed Tasks:");

        for (Task task : tasks) {
            if (task.completed) {
                System.out.println(task.name);
            }
        }
    }
}