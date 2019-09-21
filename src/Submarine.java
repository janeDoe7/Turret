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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Submarine extends Application {
    private static Stage stage = new Stage();
    private static Group root = new Group();
    private static Canvas canvas = new Canvas(1600, 900);
    private static Pane pane = new Pane();
    private static Player player;
    private static boolean press = true;
    private static ArrayList<Enemy> enemies = new ArrayList<>();
    private static double w, h;
    private static ImageView submarineView = new ImageView();
    private static int killCount = 0, score = 0;


    public static void main(String[] args) {
        launch(args);
    }

    private static void setPress(boolean value) {
        press = value;
    }

    public void spawnNewEnemy(Image enemySubImage) {
        double theta = Math.random() * 360;
        double target_x = (w - enemySubImage.getWidth()) / 2;
        double target_y = (h - enemySubImage.getHeight()) / 2;
        double pos_x = (w - enemySubImage.getWidth()) / 2 + 700 * Math.sin(Math.PI / 180 * theta);
        double pos_y = (h - enemySubImage.getHeight()) / 2 - 700 * Math.cos(Math.PI / 180 * theta);
        double factor = 1 + killCount / 20.0;
        enemies.add(new Enemy(enemySubImage,
                submarineView, pane, target_x, target_y, pos_x, pos_y, theta, factor * (Math.random() + 1.0)));
        enemies.get(enemies.size() - 1).animate();
    }

    //tests collision between two objects. destroys both if they collide.
    public static void testCollision(ImageView imageView, Enemy enemy, boolean isPlayer) {
        ImageView enemyView = enemy.getImageView();
        if (imageView.getBoundsInParent().intersects(enemyView.getBoundsInParent())) {
            pane.getChildren().remove(imageView);
            pane.getChildren().remove(enemyView);
            if (isPlayer) {
                try {
                    player.setAlive(false);
                    imageView.setRotate(0);
                    while (enemies.size() > 0) {
                       Enemy tempEnemy = enemies.get(0);
                       tempEnemy.setAlive(false);
                       enemies.remove(tempEnemy);
                       ImageView tempView = tempEnemy.getImageView();
                       tempView.setImage(null);
                       pane.getChildren().remove(tempView);
                    }
                } catch (IndexOutOfBoundsException e) {}
                pane.getChildren().remove(imageView);
                killCount = 0;
                score = 0;
            } else {
                enemy.setAlive(false);
                enemies.remove(enemy);
                enemyView.setImage(null);
                pane.getChildren().remove(imageView);
                pane.getChildren().remove(enemyView);
                killCount++;
                score += ((int)(enemy.getSpeed() * 100) / 10 * 10);
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
                background.add(new ImageView(waterImg), x, y);
            }
        }

        w = screenSize.getWidth();
        h = screenSize.getHeight();
        Canvas canvas = new Canvas(w, h);
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        root.getChildren().add(background);
        root.getChildren().add(canvas);
        root.getChildren().add(pane);
        Scene subScene = new Scene(root);
        initStage.setX(screenSize.getMinX());
        initStage.setY(screenSize.getMinY());
        initStage.setScene(subScene);
        initStage.setWidth(w);
        initStage.setHeight(h);


        // launch submarine
        Image submarineImg = new Image("File:./images/submarine.png");
        submarineView.setImage(submarineImg);
        submarineView.setFitWidth(submarineImg.getWidth());
        submarineView.setFitHeight(submarineImg.getHeight());
        submarineView.setX((w - submarineView.getFitWidth()) / 2);
        submarineView.setY((h - submarineView.getFitHeight()) / 2);
        pane.getChildren().add(submarineView);
        player = new Player(submarineView);

        // handle key pressing
        subScene.setOnKeyPressed (
                e -> {
                    // fire torpedo
                    if (e.getCode().equals(KeyCode.SPACE) && press) {
                        if (player.getAlive()) {
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
                                            y < -torpedoView.getFitHeight() || y > h)) {
                                        torpedoView.setX(torpedoView.getX() +
                                                10 * Math.sin(Math.PI / 180 * rotate));
                                        torpedoView.setY(torpedoView.getY() -
                                                10 * Math.cos(Math.PI / 180 * rotate));
                                        pane.getChildren().add(torpedoView);
                                        for (int i = 0; i < enemies.size(); i++) {
                                            testCollision(torpedoView,
                                                    enemies.get(i), false);
                                        }
                                    }
                                }
                            }.start();
                        }
                    // restart on shift
                    } else if (e.getCode().equals(KeyCode.SHIFT) && !player.getAlive()) {
                            pane.getChildren().add(submarineView);
                            player.setAlive(true);
                    // rotate left
                    } else if (e.getCode().equals(KeyCode.LEFT)) {
                        if (player.getAlive()) {
                            submarineView.setRotate(submarineView.getRotate() - 10);
                        }
                    // rotate right
                    } else if (e.getCode().equals(KeyCode.RIGHT)) {
                        if (player.getAlive()) {
                            submarineView.setRotate(submarineView.getRotate() + 10);
                        }
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

        ImageView enemySubmarineView = new ImageView("File:./images/submarine.png");

        // Add a score.
        Text scoreText = new Text();
        scoreText.setFont(new Font("Wingdings", 20));
        scoreText.setText("Score: 0");
        scoreText.setX(w * 3 / 4);
        scoreText.setY(h * 1 / 5);
        root.getChildren().add(scoreText);

        // main game loop
        new AnimationTimer() {
            public void handle(long currentNanoTime) {

                // generate new enemy at random interval
                if (Math.random() < 0.005 + 0.00025 * killCount && player.getAlive()) {
                    spawnNewEnemy(enemySubmarineView.getImage());
                }

                scoreText.setText("Score: " + score + "\n\nPress Shift to Restart");
            }
        }.start();

        initStage.show();
    }
}
