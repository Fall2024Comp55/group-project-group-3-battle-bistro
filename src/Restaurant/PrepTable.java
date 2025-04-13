package Restaurant;

import Character.Character;
import Food.Food;
import Screen.RestaurantScreen;
import Utils.Interact;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

//public class PrepTable extends GCompound implements Solid, Interact {
//    private final GRect placeholder;
//    private final String name;
//
//    public PrepTable(String name) {
//        this.name = name;
//        placeholder = new GRect(50, 50); // Placeholder size (adjust as needed)
//        placeholder.setFilled(true);
//        placeholder.setFillColor(Color.GRAY);
//        add(placeholder);
//    }
//
//    @Override
//    public void interact() {
//        if (Character.getInstance().getHolding() == null) {
//            Food pizza = new Food();
//            Character.getInstance().setHolding(pizza);
//            RestaurantUI.getInstance().showNotification("Started assembling a new pizza!", 3000);
//        } else {
//            RestaurantUI.getInstance().showNotification("You're already holding a pizza!", 3000);
//        }
//    }
//
//    @Override
//    public void onCollision() {
//    }
//
//    @Override
//    public GRectangle getHitbox() {
//        return Utils.Utils.getHitboxOffset(this.getBounds(), RestaurantScreen.getInstance().getBounds());
//    }
//
//    @Override
//    public GRectangle getInteractHitbox() {
//        return this.getBounds();
//    }
//}



public class PrepTable extends GCompound implements Interact, Solid {
    // TODO find needed variables and methods
    private static final String PATH = "/resources/restaurant/prep_table.png";

    private final GImage gImage;
    private Food item;
    private GCompound food_image;

    public PrepTable() {
        GImage gImage = new GImage(Utils.getImage(PATH));
        this.gImage = gImage;
        //gImage.setLocation(0, 0);
        gImage.setSize(50, 50);
        add(gImage);
        //TODO: REMOVE BELOW! BELOW LINES ARE JUST FOR TESTING
//        item = new Food();
//        food_image = new GImage(Utils.getImage("/resources/enemy/pizza (2).png"));
//        food_image.setLocation(gImage.getLocation());
//        food_image.setSize(gImage.getWidth(), gImage.getHeight());
//        addFood();
    }

    public void addFood() {
        add(food_image);
    }

    public void removeFood() {
        remove(food_image);
    }

    @Override
    public void interact() {
        /*
        TODO: Upon pressing "e", the player will add one ingredient to the pizza. Decrement amount
         of this ingredient by one. If not holding a pizza, do nothing.
         */
        if (item != null) {
            if (Character.getInstance().getHolding() == null) {
                Character.getInstance().setHolding(item);
                item = null;
                removeFood();
            }
        } else {
            item = Character.getInstance().getHolding();
            food_image = item.getImage();
            food_image.setLocation(gImage.getLocation());
            addFood();
            Character.getInstance().setHolding(null);
            addFood();
        }
    }

    public Image getImage() {
        URL resource = getClass().getResource(PATH);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        }
        throw new RuntimeException("Could not find image for path " + PATH);
    }

    @Override
    public void onCollision() {

    }

    @Override
    public GRectangle getHitbox() {
        return this.getBounds();
    }

    @Override
    public GRectangle getInteractHitbox() {
        return Utils.getHitboxOffset(this.getBounds(), RestaurantScreen.getInstance().getBounds());
    }

}
