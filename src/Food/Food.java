package Food;

import Customer.OrderTicket;
import acm.graphics.GCompound;

import java.util.EnumSet;

public class Food extends GCompound {

    private EnumSet<IngredientsType> ingredients;
    private OrderTicket orderTicket;
    private boolean sauce;
    private boolean cheese;
    private boolean cooked;
    private boolean boxed;

    public Food() {
        ingredients = EnumSet.noneOf(IngredientsType.class);
        orderTicket = null;
        sauce = false;
        cheese = false;
        cooked = false;
        boxed = false;
    }

    public Food(IngredientsType dough) {
        this();
        addIngredient(dough);
    }


    public void addIngredient(IngredientsType ingredient) {
        ingredients.add(ingredient);
    }

    public boolean hasIngredient(IngredientsType ingredient) {
        return ingredients.contains(ingredient);
    }

    public boolean hasSauce() {
        return sauce;
    }

    public boolean hasCheese() {
        return cheese;
    }

    public boolean isCooked() {
        return cooked;
    }

    public boolean isBoxed() {
        return boxed;
    }
}
