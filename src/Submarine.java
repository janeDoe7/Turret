import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.EventListener;

public class Submarine extends Application {
    private static Stage stage = new Stage();
    private static Group root = new Group();
    private static Canvas canvas = new Canvas(1600, 900);
    private static Pane pane = new Pane();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage initStage) {
        initStage = stage;
        initStage.setTitle("Submarine!");
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        double w = screenSize.getWidth();
        double h = screenSize.getHeight();
        Canvas canvas = new Canvas(w, h);
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        root.getChildren().add(pane);
        Scene scene = new Scene(root);
        initStage.setX(screenSize.getMinX());
        initStage.setY(screenSize.getMinY());
        initStage.setScene(scene);
        initStage.setWidth(w);
        initStage.setHeight(h);
        initStage.show();

        ImageView submarineView = new ImageView();
        Image submarineImg = new Image("File:./images/submarine.png");
        submarineView.setImage(submarineImg);
        submarineView.setX(w / 2 - submarineImg.getWidth() / 2);
        submarineView.setY(h / 2 - submarineImg.getHeight() / 2);
        pane.getChildren().add(submarineView);

        scene.setOnKeyPressed(
                event -> submarineView.setRotate(submarineView.getRotate() + 90)
        );
    }

}
