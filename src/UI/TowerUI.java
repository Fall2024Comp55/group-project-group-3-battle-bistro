package UI;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import Tower.Tower;

import java.awt.*;

public class TowerUI extends UI {
    private static final TowerUI TOWER_UI;
    private ActionButton upgradeButton;
    private GLabel costLabel;

    static {
        try {
            TOWER_UI = new TowerUI();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating TowerUI singleton instance");
        }
    }

    private TowerUI() {
        GRect background = new GRect(100, 60); // Increased size for button and label
        background.setFilled(true);
        background.setFillColor(Color.WHITE);
        add(background);
        initializeComponents();
    }

    public static TowerUI getInstance() {
        return TOWER_UI;
    }

    @Override
    public void initializeComponents() {
        upgradeButton = new ActionButton("Upgrade", () -> {
            Tower tower = Tower.getSelectedTower();
            if (tower != null) {
                tower.upgrade();
                update(); // Update UI after upgrade
            }
        });
        add(upgradeButton, 10, 10);

        costLabel = new GLabel("");
        costLabel.setFont("Arial-12");
        costLabel.setColor(Color.BLACK);
        add(costLabel, 10, 40);
    }

    @Override
    public void update() {
        Tower tower = Tower.getSelectedTower();
        if (tower != null) {
            int cost = tower.getUpgradeCost();
            costLabel.setLabel(cost > 0 ? "Cost: " + cost + "$" : "Max Level");
            upgradeButton.setVisible(cost > 0); // Hide button if max level
        }
    }
}