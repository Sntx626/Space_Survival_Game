package GameEngine;

import GameEngine.Entitys.Entity;
import Viewer.Renderer;

import java.util.ArrayList;

public class World extends Frame{


    public World(Renderer renderer) {
        super(renderer);
    }

    @Override
    public void init() {
        this.setWindowTitle("This is a Game");
    }
}