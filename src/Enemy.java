public class Enemy {
    private ImageView imgView;
    private Point target;
    private Point[] path;
    private int pathCounter = 0; // how many points have we looked through?
    private double speed;

    public Enemy(Image, target_x, target_y, pos_x, pos_y, speed)  {
        this.imgView = new ImageView(imgView);
        this.imgView.setX(pos_x);
        this.imgView.setY(pos_Y);
        this.target = Point(target_x, target_y);
        this.path = constructPoints();
        this.speed = speed;
    }

    private Point[] constructPoints(int start_x, int start_y) {
        int internal_speed_constant = 10;
        int max_i = internal_speed_constant / this.speed;
        Point[] points = new Point[max_x]
        for (int i = 0; x < max_i; i++) {
            points[i] = new Point((start_x - target_x) * (i+1) / max_i, (start_y - target_y) * (i+1) / max_i);
        }
        return points
    }

    public Point getNextLocation() {
        pathCounter++;
        if (pathCounter > points.length) {
            return path[path.length - 1];
        } else {
            return path[pathCounter - 1];
        }
    }

    public ImageView getImageView() { return this.imgView; }
}
