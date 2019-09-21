import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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

        // The background
        GridPane background = new GridPane();
        background.setHgap(0);
        background.setVgap(0);

        ImageView waterView = new ImageView();
        Image waterImg = new Image("File:./images/water.jpg");
        waterView.setImage(waterImg);
        for (int x = 0; x < 6; x++)
            for (int y = 0; y < 3; y++) {
                background.add(waterImg);
            }
        Scene backgroundScene = new Scene(background);
        initStage.setScene(backgroundScene);

        double w = screenSize.getWidth();
        double h = screenSize.getHeight();
        Canvas canvas = new Canvas(w, h);
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        root.getChildren().add(pane);
        Scene subScene = new Scene(root);
        initStage.setX(screenSize.getMinX());
        initStage.setY(screenSize.getMinY());
        initStage.setScene(scene);
        initStage.setWidth(w);
        initStage.setHeight(h);
        initStage.show();


        // The submarine launches
        ImageView submarineView = new ImageView();
        Image submarineImg = new Image("File:./images/submarine.png");
        submarineView.setImage(submarineImg);
        submarineView.setX(w / 2 - submarineImg.getWidth() / 2);
        submarineView.setY(h / 2 - submarineImg.getHeight() / 2);
        pane.getChildren().add(submarineView);

        // Key handling
        subScene.setOnKeyPressed(
                e -> {
                    // rotate left
                    if (e.getCode().equals(KeyCode.LEFT)) {
                        submarineView.setRotate(submarineView.getRotate() - 15);
                    // rotate right
                    } else if (e.getCode().equals(KeyCode.RIGHT)) {
                        submarineView.setRotate(submarineView.getRotate() + 15);
                    }
                }
        );
    }

}
