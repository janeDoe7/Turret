import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.EventListener;

public class Submarine extends Application {
    private static Stage stage = new Stage();
    private static Group root = new Group();
    private static Canvas canvas = new Canvas(1600, 900);
    private static Pane pane = new Pane();
    private static boolean press = true, playerIsAlive = true;
    private static boolean[] enemyIsAlive;

    public static void main(String[] args) {
        launch(args);
    }

    private static void setPress(boolean value) {
        press = value;
    }

    //tests collision between two objects. destroys both if they collide.
    private void testCollision(ImageView imageView1, ImageView imageView2, boolean isPlayer, int index) {
        if (imageView1.getBoundsInParent().intersects(imageView2.getBoundsInParent())) {
            pane.getChildren().remove(imageView1);
            pane.getChildren().remove(imageView2);
            if (isPlayer) {
                playerIsAlive = false;
            } else {
                enemyIsAlive[index] = false;
            }
        }
    }

    public void start(Stage initStage) {
        initStage = stage;
        initStage.setTitle("Submarine!");
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

        // The background
        GridPane background = new GridPane();
        background.setHgap(0);
        background.setVgap(0);

        Image waterImg = new Image("File:./images/water.jpg");
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 3; y++) {
                background.add(new ImageView(waterImg), y, x);
            }
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
        initStage.setScene(subScene);
        initStage.setWidth(w);
        initStage.setHeight(h);
        initStage.show();

        // enemy submarine
        // TODO: get rid of this
        // Will be replaced by randomly generated enemies
        ImageView enemySubmarineView = new ImageView("File:./images/submarine.png");
        enemySubmarineView.setX(w / 2 - enemySubmarineView.getImage().getWidth() / 2);
        pane.getChildren().add(enemySubmarineView);
        enemyIsAlive = new boolean[1];
        enemyIsAlive[0] = true;
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if (enemyIsAlive[0]) {
                    pane.getChildren().remove(enemySubmarineView);
                    enemySubmarineView.setY(enemySubmarineView.getY() + 1);
                    pane.getChildren().add(enemySubmarineView);
                }
            }
        }.start();

        // launch submarine
        ImageView submarineView = new ImageView();
        Image submarineImg = new Image("File:./images/submarine.png");
        submarineView.setImage(submarineImg);
        submarineView.setFitWidth(submarineImg.getWidth());
        submarineView.setFitHeight(submarineImg.getHeight());
        submarineView.setX(w / 2 - submarineView.getFitWidth() / 2);
        submarineView.setY(h / 2 - submarineView.getFitHeight() / 2);
        pane.getChildren().add(submarineView);

        // handle key pressing
        subScene.setOnKeyPressed (
                e -> {
                    // fire torpedo
                    if (e.getCode().equals(KeyCode.SPACE) && press) {
                        setPress(false);
                        double rotate = submarineView.getRotate();
                        Image torpedoImg = new Image("File:./images/torpedo.png");
                        ImageView torpedoView = new ImageView(torpedoImg);
                        torpedoView.setFitWidth(torpedoImg.getWidth() / 50);
                        torpedoView.setFitHeight(torpedoImg.getHeight() / 50);
                        torpedoView.setX((w - torpedoView.getFitWidth() +
                                (submarineView.getFitHeight() + torpedoView.getFitHeight()) *
                                        Math.sin(Math.PI / 180 * rotate)) / 2);
                        torpedoView.setY((h - torpedoView.getFitWidth() -
                                (submarineView.getFitHeight() + torpedoView.getFitHeight()) *
                                        Math.cos(Math.PI / 180 * rotate)) / 2);
                        torpedoView.setRotate(rotate);
                        pane.getChildren().add(torpedoView);

                        // animate torpedo
                        new AnimationTimer() {
                            public void handle(long currentNanoTime) {
                                double x = torpedoView.getX();
                                double y = torpedoView.getY();
                                // remove previous torpedo frame
                                pane.getChildren().remove(torpedoView);

                                // do nothing if off screen else move torpedo
                                if (!(x < -torpedoView.getFitWidth() || x > w ||
                                        y < - torpedoView.getFitHeight() || y > h)) {
                                    torpedoView.setX(torpedoView.getX() +
                                            10 * Math.sin(Math.PI / 180 * rotate));
                                    torpedoView.setY(torpedoView.getY() -
                                            10 * Math.cos(Math.PI / 180 * rotate));
                                    pane.getChildren().add(torpedoView);
                                    if (enemyIsAlive[0]) {
                                        testCollision(torpedoView, enemySubmarineView, false, 0);
                                    }
                                }
                            }
                        }.start();
                    // rotate left
                    } else if (e.getCode().equals(KeyCode.LEFT)) {
                        submarineView.setRotate(submarineView.getRotate() - 15);
                    // rotate right
                    } else if (e.getCode().equals(KeyCode.RIGHT)) {
                        submarineView.setRotate(submarineView.getRotate() + 15);
                    }
                }
        );

        //make sure user has released SPACE before firing again
        subScene.setOnKeyReleased(
                e -> {
                    if (e.getCode().equals(KeyCode.SPACE)) {
                        setPress(true);
                    }
                }
        );
    }

}