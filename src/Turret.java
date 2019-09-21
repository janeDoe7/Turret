import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Turret extends Application {
    private static BorderPane borderPane = new BorderPane();
    private static GridPane gridPane = new GridPane();
    private Scene scene = new Scene(borderPane);
    private static Stage stage = new Stage();

    public void start(Stage initStage) {
        stage = initStage;
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void launch(String[] args) {
    }

}
