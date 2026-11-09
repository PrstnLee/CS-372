package Week_2;

public class Task {

    private String name;
    private boolean priority;
    private boolean completed;

    public Task(String name, boolean priority) {
        this.name = name;
        this.priority = priority;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public boolean getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        String status = completed ? "[DONE]" : "[ACTIVE]";

        return status + " " + name +
                " | Priority: " + priority;
    }
}
