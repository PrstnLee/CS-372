package Week_5.UI;

import Week_5.UI.TaskController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Week_5.Managers.TaskManager;
import Week_5.Storage.TaskFileHandler;
import Week_5.UI.TaskView;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        // Initialize application components
        TaskManager taskManager = new TaskManager();
        TaskFileHandler fileHandler = new TaskFileHandler();

        // Create the interface
        TaskView taskView = new TaskView();

        // Connect interface to application logic
        new TaskController(
                taskManager,
                taskView,
                fileHandler
        );

        // Display application
        Scene scene = new Scene(
                taskView.getLayout(),
                800,
                450
        );

        stage.setTitle("Mini Task Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
