package Food;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public enum IngredientsType {
    DOUGH,
    MOZZARELLA,
    PEPPERONI,
    MUSHROOM;

    private static final String ingredientsBasePath = "/resources/ingredients/";
    private static final String stationBasePath = "/stations/";
    private static final String extension = ".png";

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
            case DOUGH -> stationBasePath + "dough" + extension;
            case MOZZARELLA -> stationBasePath + "mozzarella" + extension;
            case PEPPERONI -> stationBasePath + "pepperoni" + extension;
            case MUSHROOM -> stationBasePath + "mushroom" + extension;
        };
    }

    public String toIngredientPath() {
        return switch (this) {
            case DOUGH -> ingredientsBasePath + "dough" + extension;
            case MOZZARELLA -> ingredientsBasePath + "mozzarella" + extension;
            case PEPPERONI -> ingredientsBasePath + "pepperoni" + extension;
            case MUSHROOM -> ingredientsBasePath + "mushroom" + extension;
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
