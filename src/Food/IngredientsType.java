package Food;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public enum IngredientsType {
    DOUGH,
    MOZZARELLA,
    PEPPERONI,
    MUSHROOM;

    private static final String INGREDIENTS_BASE_PATH = "/resources/ingredients/";
    private static final String STATION_BASE_PATH = "/stations/";
    private static final String EXTENSION = ".png";

    IngredientsType() {
    }

    public String toString() {
        return switch (this) {
            case DOUGH -> "Dough";
            case MOZZARELLA -> "Mozzarella";
            case PEPPERONI -> "Pepperoni";
            case MUSHROOM -> "Mushroom";
        };
    }

    public String toStationPath() {
        return switch (this) {
            case DOUGH -> STATION_BASE_PATH + "dough" + EXTENSION;
            case MOZZARELLA -> STATION_BASE_PATH + "mozzarella" + EXTENSION;
            case PEPPERONI -> STATION_BASE_PATH + "pepperoni" + EXTENSION;
            case MUSHROOM -> STATION_BASE_PATH + "mushroom" + EXTENSION;
        };
    }

    public String toIngredientPath() {
        return switch (this) {
            case DOUGH -> INGREDIENTS_BASE_PATH + "dough" + EXTENSION;
            case MOZZARELLA -> INGREDIENTS_BASE_PATH + "mozzarella" + EXTENSION;
            case PEPPERONI -> INGREDIENTS_BASE_PATH + "pepperoni" + EXTENSION;
            case MUSHROOM -> INGREDIENTS_BASE_PATH + "mushroom" + EXTENSION;
        };
    }

    public Image getStationImage() {
        URL resource = getClass().getResource(toStationPath());
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        }
        throw new RuntimeException("Could not find image for path " + toStationPath());
    }

    public Image getIngredientImage() {
        URL resource = getClass().getResource(toIngredientPath());
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        }
        throw new RuntimeException("Could not find image for path " + toIngredientPath());
    }

}
