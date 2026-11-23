package Week_5.Storage;

import Week_5.Models.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskFileHandler {

    private final File taskFile = new File("tasks.csv");

    // Save tasks
    public void saveTasks(List<Task> tasks)
            throws IOException {

        try (PrintWriter writer = new PrintWriter(
                new FileWriter(taskFile))) {

            writer.println("Name,Priority,Completed");

            for (Task task : tasks) {

                String escapedName = task.getName()
                        .replace("\"", "\"\"");

                writer.println(
                        "\"" + escapedName + "\"," +
                                task.getPriority() + "," +
                                task.isCompleted()
                );
            }
        }
    }

    // Load tasks
    public List<Task> loadTasks()
            throws IOException {

        if (!taskFile.exists()) {
            throw new FileNotFoundException(
                    "No saved tasks file found."
            );
        }

        List<Task> loadedTasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(taskFile))) {

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

                Task task = new Task(
                        name,
                        Boolean.parseBoolean(priorityText)
                );

                task.setCompleted(
                        Boolean.parseBoolean(completedText)
                );

                loadedTasks.add(task);
            }
        }

        return loadedTasks;
    }

    // Parse CSV values, including quoted names
    private String[] parseCsvLine(String line)
            throws IOException {

        List<String> values = new ArrayList<>();

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
}
