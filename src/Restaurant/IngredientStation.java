package Restaurant;

import Character.Character;
import Food.Food;
import Food.IngredientsType;
import Screen.RestaurantScreen;
import Utils.Interact;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class IngredientStation extends GCompound implements Interact, Solid {
    private static final String BASE_PATH = "/resources/restaurant/";
    private static final String EXTENSION = ".png";

    private final IngredientsType ingredient;
    private final GImage gImage;

    public IngredientStation(IngredientsType ingredient) {
        this.ingredient = ingredient;
        GImage gImage = new GImage(Utils.getImage(ingredient.toStationPath()));
        this.gImage = gImage;
        //gImage.setLocation(0, 0);
        gImage.setSize(60, 60);
        add(gImage);
    }

    @Override
    public void interact() {
        Food holding = Character.getInstance().getHolding();
        if (ingredient == IngredientsType.DOUGH && holding == null &&
                Character.getInstance().removeIngredient(ingredient, 1)) {
            Food pizza = new Food();
            Character.getInstance().setHolding(pizza);
        } else if (holding != null && !holding.getIngredients().contains(ingredient) &&
                !holding.isBoxed() && !holding.isCooked() &&
                Character.getInstance().removeIngredient(ingredient, 1)) {
            holding.addIngredient(ingredient);
        }
    }

    public String toPath() {
        return BASE_PATH + ingredient.name().toLowerCase() + "_station" + EXTENSION;
    }

    @Override
    public void onCollision() {

    }

    @Override
    public GRectangle getHitbox() {
        return Utils.getHitboxOffset(this.getBounds(), RestaurantScreen.getInstance().getBounds());
    }

    @Override
    public GRectangle getInteractHitbox() {
        return Utils.getHitboxOffset(this.getBounds(), RestaurantScreen.getInstance().getBounds());
    }
}
