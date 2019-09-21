import javafx.scene.image.ImageView;

public class Player {
    private ImageView imgView;
    private boolean isAlive = true;

    Player(ImageView imgView) {
        this.imgView = imgView;
    }

    public boolean getAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
