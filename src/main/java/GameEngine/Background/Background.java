package GameEngine.Background;

import GameEngine.Frame;

public class Background {

    Frame frame;
    String path;
    int width, height;
    boolean animated;


    public Background(Frame frame, String path, int width, int height, boolean animated) {
        this.frame = frame;
        this.path = path;
        this.width = width;
        this.height = height;
        this.animated = animated;
    }

    
}
