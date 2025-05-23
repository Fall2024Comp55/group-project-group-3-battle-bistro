package Food;

import Customer.OrderTicket;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;

import java.util.EnumSet;

public class Food extends GCompound {
    public static final String PIZZA_BOX_PATH = "/resources/restaurant/pizza_box_down.png";
    private final EnumSet<IngredientsType> ingredients;
    private OrderTicket orderTicket;
    private boolean cooked;
    private boolean boxed;

    public EnumSet<IngredientsType> getIngredients() {
        return ingredients;
    }

    public Food() {
        ingredients = EnumSet.noneOf(IngredientsType.class);
        // add default ingredients
        addIngredient(IngredientsType.DOUGH);
        cooked = false;
        boxed = false;
    }

    public void addIngredient(IngredientsType ingredient) {
        if (!isCooked() && !isBoxed()) {
            ingredients.add(ingredient);
            updateFoodImage();
        }
    }

    public void updateFoodImage() {
        removeAll();
        for (IngredientsType ingredient : ingredients) {
            GImage ingredientImage = new GImage(Utils.getImage(ingredient.toIngredientPath()));
            ingredientImage.setSize(40, 40);
            add(ingredientImage);
        }
    }

    public boolean hasIngredient(IngredientsType ingredient) {
        return ingredients.contains(ingredient);
    }

    public boolean isCooked() {
        return cooked;
    }

    public boolean isBoxed() {
        return boxed;
    }

    public void setOrderTicket(OrderTicket orderTicket) {
        this.orderTicket = orderTicket;
    }

    public OrderTicket getOrderTicket() {
        return orderTicket;
    }

    public void setCooked(boolean cooked) {
        this.cooked = cooked;
    }

    public void box() {
        removeAll();
        GImage boxedImage = new GImage(Utils.getImage(PIZZA_BOX_PATH));
        boxedImage.setSize(40, 40);
        add(boxedImage);
        this.boxed = true;
    }

    public void unbox() {
        removeAll();
        updateFoodImage();
        this.boxed = false;
    }
}