package Week_2;

public class Task {
    String name;
    String description;
    boolean completed;
    boolean priority;

    public Task(String name, String description, boolean priority) {
        this.name = name;
        this.description = description;
        completed = false;
        this.priority = priority;
    }

    public void completeTask() {
        completed = true;
    }
}
