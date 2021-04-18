package GameEngine.World;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class ImageCache {

    ArrayList<Image> entity_data = new ArrayList<Image>();

    public ImageCache() {
        entity_data.add(new Image("file:rsc/entity_data/PH.png"));
        entity_data.add(new Image("file:rsc/entity_data/asteroid_0.png"));
        entity_data.add(new Image("file:rsc/entity_data/asteroid_1.png"));
        entity_data.add(new Image("file:rsc/entity_data/asteroid_2.png"));
        entity_data.add(new Image("file:rsc/entity_data/asteroid_3.png"));
        entity_data.add(new Image("file:rsc/entity_data/asteroid_4.png"));
        entity_data.add(new Image("file:rsc/entity_data/asteroid_5.png"));
        entity_data.add(new Image("file:rsc/entity_data/asteroid_6.png"));
        entity_data.add(new Image("file:rsc/entity_data/asteroid_7.png"));
        entity_data.add(new Image("file:rsc/entity_data/asteroid_8.png"));
        entity_data.add(new Image("file:rsc/entity_data/asteroid_9.png"));
        entity_data.add(new Image("file:rsc/entity_data/fog.png"));
        entity_data.add(new Image("file:rsc/entity_data/player.png"));
        entity_data.add(new Image("file:rsc/entity_data/rocket.png"));
    }


    public Image getImage(String imageName) {
        for (Image i : this.entity_data) {
            if (i.getUrl().equals(imageName)) {
                return i;
            }
        }
        return entity_data.get(0);
    }
}
