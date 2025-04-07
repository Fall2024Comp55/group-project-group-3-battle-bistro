package Restaurant;

import Character.Character;
import Food.Food;
import Screen.RestaurantScreen;
import UI.RestaurantUI;
import Utils.Interact;
import Utils.Solid;
import acm.graphics.GCompound;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

import java.awt.*;

public class PrepTable extends GCompound implements Solid, Interact {
    private final GRect placeholder;
    private final String name;

    public PrepTable(String name) {
        this.name = name;
        placeholder = new GRect(50, 50); // Placeholder size (adjust as needed)
        placeholder.setFilled(true);
        placeholder.setFillColor(Color.GRAY);
        add(placeholder);
    }

    @Override
    public void interact() {
        if (Character.getInstance().getHolding() == null) {
            Food pizza = new Food();
            Character.getInstance().setHolding(pizza);
            RestaurantUI.getInstance().showNotification("Started assembling a new pizza!", 3000);
        } else {
            RestaurantUI.getInstance().showNotification("You're already holding a pizza!", 3000);
        }
    }

    @Override
    public void onCollision() {
    }

    @Override
    public GRectangle getHitbox() {
        return Utils.Utils.getHitboxOffset(this.getBounds(), RestaurantScreen.getInstance().getBounds());
    }

    @Override
    public GRectangle getInteractHitbox() {
        return this.getBounds();
    }
}



//public class PrepTable extends GCompound implements Interact, Solid {
//    // TODO find needed variables and methods
//    private static final String PATH = "/resources/restaurant/prep_table.png";
//
//    private final GImage gImage;
//    private Food item;
//
//    public PrepTable() {
//        GImage gImage = new GImage(getImage());
//        this.gImage = gImage;
//        //gImage.setLocation(0, 0);
//        add(gImage);
//    }
//
//    @Override
//    public void interact() {
//        /*
//        TODO: Upon pressing "e", the player will add one ingredient to the pizza. Decrement amount
//         of this ingredient by one. If not holding a pizza, do nothing.
//         */
//        if (item != null) {
//            Character.getInstance().setHolding(item);
//            item = null;
//        } else {
//            item = Character.getInstance().getHolding();
//            Character.getInstance().setHolding(null);
//        }
//    }
//
//    public Image getImage() {
//        URL resource = getClass().getResource(PATH);
//        if (resource != null) {
//            return new ImageIcon(resource).getImage();
//        }
//        throw new RuntimeException("Could not find image for path " + PATH);
//    }
//
//    @Override
//    public void onCollision() {
//
//    }
//
//    @Override
//    public GRectangle getHitbox() {
//        return this.getBounds();
//    }
//
//    @Override
//    public GRectangle getInteractHitbox() {
//        return null;
//    }
//
//}
