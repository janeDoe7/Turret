import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {
    private ImageView imgView;
    private Point target;
    private Point[] path;
    private int pathCounter = 0; // how many points have we looked through?
    private double speed;

    public Enemy(Image image, double target_x, double target_y, double pos_x, double pos_y, double theta, double speed)  {
        this.imgView = new ImageView(image);
        this.imgView.setX(pos_x);
        this.imgView.setY(pos_y);
        this.target = new Point((int)target_x, (int)target_y);
        /*
        this.path = constructPoints((int)pos_x, (int)pos_y);
         */
        this.speed = speed;
        this.imgView.setRotate(180 + theta);
    }

    /*private Point[] constructPoints(int start_x, int start_y) {
        // using Bresenham's line algorithm to approximate a path
        int s_x, s_y, e_x, e_y, dx, dy, i_1, i_2, d = 0;
        s_x = start_x;
        s_y = start_y;
        e_x = target.getX();
        e_y = target.getY();
        dx = e_x - s_x;
        dy = e_y - s_y;
        i_1 = 2 * dy;
        i_2 = 2 * (dy - dx);
        d = i_1 - dx;

        if (dx < 0) {
            int x = e_x;
            int x_end = s_x;
            int y = e_y;
        }

    }*/

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
