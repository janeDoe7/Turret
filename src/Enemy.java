public class Enemy {
    private ImageView imgView;
    private Point target;
    private Point[] path;
    private int[] pathCounter = 0; // how many points have we looked through?
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
        // using Bresenham's line algorithm to approximate a path
        int s_x, s_y, e_x, e_y, dx, dy, i_1, i_2, d = 0;
        int s_x = start_x;
        int s_y = start_y;
        int e_x = target.getX();
        int e_y = target.getY();
        int dx = e_x - s_x;
        int dy = e_y - s_y;
        int i_1 = 2 * dy;
        int i_2 = 2 * (dy - dx);
        int d = i_1 - dx;

        if (dx < 0) {
            int x = e_x;
            int x_end = s_x;
            int y = e_y;
        }
        
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
