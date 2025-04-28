package Restaurant;

import Food.Food;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class OrderTable extends GCompound implements Solid {
    // TODO find needed variables and methods
    private static final String PATH = "/resources/restaurant/prep_table.png";

    private final GImage gImage;
    protected Food item;

    public OrderTable() {
        GImage gImage = new GImage(Utils.getImage(PATH));
        this.gImage = gImage;
//        gImage.setSize(32, 425);
        //        order_image.setLocation(490, 50);
        gImage.setSize(350, 50);
        gImage.rotate(270);
        //gImage.setLocation(0, 0);
        add(gImage);
    }

    @Override
    public void onCollision() {

    }

    @Override
    public GRectangle getHitbox() {
        return null;
    }
}
