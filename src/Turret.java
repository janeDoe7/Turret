import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Turret extends Application {
    private static Stage stage = new Stage();
    private static Group root = new Group();
    private static Canvas canvas = new Canvas(1600, 900);

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage initStage) {
        initStage = stage;
        initStage.setTitle("Turret");
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        double w = screenSize.getWidth();
        double h = screenSize.getHeight();
        Canvas canvas = new Canvas(w, h);
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        initStage.setX(screenSize.getMinX());
        initStage.setY(screenSize.getMinY());
        initStage.setScene(scene);
        initStage.setWidth(w);
        initStage.setHeight(h);
        initStage.show();

        Image turretImg = new Image("File:./images/submarine.png");
        ctx.drawImage(turretImg, (w / 2) - (turretImg.getWidth() / 2), (h / 2) - (turretImg.getHeight() / 2));


    }

}
