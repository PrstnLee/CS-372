package Week_2;

import Week_2.*;

public class MainWk2 {
    public static void main(String[] args) {
        // GitHub Link: https://github.com/PrstnLee/CS-372

        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Homework", "Do homework", true);
        Task task2 = new Task("Garbage", "Take out garbage", false);
        Task task3 = new Task("Dishes", "Clean dishes", true);
        Task task4 = new Task("Groceries", "Buy groceries", false);
        Task task5 = new Task("Sports Game", "Watch sports game", false);

        task2.completeTask();
        task4.completeTask();

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.addTask(task5);

        taskManager.displayTasks();
        System.out.println("Total Tasks: " + taskManager.tasks.size());
        System.out.println("\n");
        taskManager.filterPriority();
        System.out.println("Total Priority Tasks: " + taskManager.getPriorityNumber());
        System.out.println("\n");
        taskManager.filterCompleted();
        System.out.println("Total Completed Tasks: " + taskManager.getCompletedNumber());
    }
}