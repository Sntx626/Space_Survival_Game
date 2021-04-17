package GameEngine.UI;

import javafx.scene.canvas.GraphicsContext;

public class Component {


    String type;
    boolean enabled = true;

    int cx, cy, cw, ch, w, h;

    public Component(String type) {
        this.type = type;
    }

    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h){
        switch (this.type) {
            case "TextBox":
                break;
            default:
                System.out.println("Component type unknown!");
                break;
        }
    }

    public String getType() {
        return type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public int getCy() {
        return cy;
    }

    public void setCy(int cy) {
        this.cy = cy;
    }

    public int getCw() {
        return cw;
    }

    public void setCw(int cw) {
        this.cw = cw;
    }

    public int getCh() {
        return ch;
    }

    public void setCh(int ch) {
        this.ch = ch;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
