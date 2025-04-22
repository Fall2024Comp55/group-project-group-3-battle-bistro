package UI;


import acm.graphics.GRect;

import java.awt.*;

public class TowerUI extends UI {
    private static final TowerUI TowerUI;


    static {
        try {
            TowerUI = new TowerUI();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating TowerUI singleton instance");
        }
    }

     private TowerUI() {
         GRect background = new GRect(64, 40);
         background.setFilled(true);
         background.setFillColor(Color.WHITE);
         add(background);
         initializeComponents();
    }

    public static TowerUI getInstance() {
        return TowerUI;
    }

    @Override
    public void initializeComponents() {

    }

    @Override
    public void update() {

    }

    // TODO find needed variables and methods

}
