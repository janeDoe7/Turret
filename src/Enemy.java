import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Enemy {
    private ImageView imgView;
    private Pane pane;
    private Point target;
    private Point[] path;
    private int pathCounter = 0; // how many points have we looked through?
    private double speed;

    public Enemy(Image img,
                 Pane pane, double target_x, double target_y, double pos_x, double pos_y, double theta, double speed)  {
        this.imgView = new ImageView(img);
        this.pane = pane;
        this.imgView.setX(pos_x);
        this.imgView.setY(pos_y);
        this.target = new Point((int)target_x, (int)target_y);
        this.speed = speed;
        this.path = constructPoints((int)pos_x, (int)pos_y);
        this.imgView.setRotate(theta + 180);
        animate();
    }

    private Point[] constructPoints(int start_x, int start_y) {
        double internal_speed_constant = 500.0;
        int max_i = (int)(internal_speed_constant / speed);
        Point[] points = new Point[max_i];
        for (int i = 0; i < max_i; i++) {
            points[i] = new Point(start_x + (target.getX() - start_x) * (i+1) / max_i, start_y + (target.getY() - start_y) * (i+1) / max_i);
        }
        return points;
    }

    public Point getNextLocation() {
        pathCounter++;
        if (pathCounter > path.length) {
            return path[path.length - 1];
        } else {
            return path[pathCounter - 1];
        }
    }

    public ImageView getImageView() { return this.imgView; }

    public void animate() {
        pane.getChildren().add(imgView);
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                Point p = getNextLocation();
                imgView.setX(p.getX());
                imgView.setY(p.getY());
            }
        }.start();
    }
}
