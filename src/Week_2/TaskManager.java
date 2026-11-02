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

    public void displayTasks() {
        System.out.println("All tasks:");

        for (Task task : tasks) {
            System.out.println(task.name + " (" + task.description + ")");
        }
    }

    public void filterPriority() {
        System.out.println("High Priority Tasks:");

        for (Task task : tasks) {
            if (task.priority) {
                System.out.println(task.name + " (" + task.description + ")");
            }
        }
    }

    public int getPriorityNumber() {
        int count = 0;

        for (Task task : tasks) {
            if (task.priority) {
                count++;
            }
        }

        return count;
    }

    public void filterCompleted() {
        System.out.println("Completed Tasks:");

        for (Task task : tasks) {
            if (task.completed) {
                System.out.println(task.name + " (" + task.description + ")");
            }
        }
    }

    public int getCompletedNumber() {
        int count = 0;

        for (Task task : tasks) {
            if (task.completed) {
                count++;
            }
        }

        return count;
    }
}