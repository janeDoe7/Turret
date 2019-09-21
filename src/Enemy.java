import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {
    private ImageView imgView;
    private Point target;
    private Point[] path;
    private int pathCounter = 0; // how many points have we looked through?
    private double speed;

    public Enemy(Image img, double target_x, double target_y, double pos_x, double pos_y, double theta, int speed)  {
        this.imgView = new ImageView(img);
        this.imgView.setX(pos_x);
        this.imgView.setY(pos_y);
        this.target = new Point((int)target_x, (int)target_y);
        this.path = constructPoints((int)pos_x, (int)pos_y);
        this.imgView.setRotate(theta + 180);
        this.speed = speed;
    }

    private Point[] constructPoints(int start_x, int start_y) {
        int internal_speed_constant = 10;
        int max_i = (int)(internal_speed_constant / this.speed);
        Point[] points = new Point[max_i];
        for (int i = 0; i < max_i; i++) {
            points[i] = new Point((start_x - target.getX()) * (i+1) / max_i, (start_y - target.getY()) * (i+1) / max_i);
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
}
