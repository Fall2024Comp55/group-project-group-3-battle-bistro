package Food;

import Customer.OrderTicket;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;

import java.util.EnumSet;

public class Food extends GCompound {
    private final EnumSet<IngredientsType> ingredients;
    private OrderTicket orderTicket;
    private boolean sauce;
    private boolean cheese;
    private boolean pepperoni;
    private boolean mushroom;
    private boolean cooked;
    private boolean boxed;
    private final GCompound image;

    public EnumSet<IngredientsType> getIngredients() {
        return ingredients;
    }

    public Food() {
        ingredients = EnumSet.noneOf(IngredientsType.class);
        image = new GCompound();
        ingredients.add(IngredientsType.DOUGH);
        sauce = false;
        cheese = false;
        pepperoni = false;
        mushroom = false;
        cooked = false;
        boxed = false;
    }

    public GCompound getImage() {
        return image;
    }

//    public Food(IngredientsType dough) {
//        this();
//        addIngredient(dough);
//    }


    public void addIngredient(IngredientsType ingredient) {
        ingredients.add(ingredient);
        GImage ingred = new GImage(Utils.getImage(ingredient.toIngredientPath()));
        ingred.setSize(40, 40);
        image.add(ingred);
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

    public void setOrderTicket(OrderTicket orderTicket) {
        this.orderTicket = orderTicket;
    }

    public boolean isMushroom() {
        return mushroom;
    }

    public void setMushroom(boolean mushroom) {
        this.mushroom = mushroom;
        this.ingredients.add(IngredientsType.MUSHROOM);
    }

    public boolean isPepperoni() {
        return pepperoni;
    }

    public void setPepperoni(boolean pepperoni) {
        this.pepperoni = pepperoni;
        this.ingredients.add(IngredientsType.PEPPERONI);
    }

    public void setSauce(boolean sauce) {
        this.sauce = sauce;
        this.ingredients.add(IngredientsType.SAUCE);
    }

    public void setCheese(boolean cheese) {
        this.cheese = cheese;
        this.ingredients.add(IngredientsType.MOZZARELLA);
    }

    public void setCooked(boolean cooked) {
        this.cooked = cooked;
    }

    public void setBoxed(boolean boxed) {
        this.boxed = boxed;
    }
}