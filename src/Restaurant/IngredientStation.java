package Restaurant;

import Character.Character;
import Food.IngredientsType;
import Screen.RestaurantScreen;
import Utils.Interact;
import Utils.Solid;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IngredientStation extends GCompound implements Interact, Solid {
    // TODO find needed variables and methods
    private static final String BASE_PATH = "/resources/restaurant/";
    private static final String EXTENSION = ".png";

    private final IngredientsType ingredient;
    private final GImage gImage;

    public IngredientStation(IngredientsType ingredient) {
        this.ingredient = ingredient;
        GImage gImage = new GImage(getImage());
        this.gImage = gImage;
        //gImage.setLocation(0, 0);
        add(gImage);
    }

    @Override
    public void interact() {
        /*
        TODO: Upon pressing "e", the player will add one ingredient to the pizza. Decrement amount
         of this ingredient by one. If not holding a pizza, do nothing.
         */
        if (Character.getInstance().removeIngredient(ingredient, 1)) {
            Character.getInstance().getHolding().addIngredient(ingredient);
        }
        //REMEMBER TO REMOVE RESOURCE/INGREDIENT FROM TOTLA
    }

    public String toPath() {
        return BASE_PATH + ingredient.name().toLowerCase() + "_station" + EXTENSION;
    }

    public Image getImage() {
        URL resource = getClass().getResource(toPath());
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        }
        throw new RuntimeException("Could not find image for path " + toPath());
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
        return null;
    }
}
