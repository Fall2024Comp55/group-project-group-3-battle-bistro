package UI;

import Character.Character;
import Screen.GardenScreen;
import Tower.MeleeTower;
import Tower.RangedTower;
import Tower.Tower;
import Utils.MouseInteract;
import Utils.MouseManager;
import acm.graphics.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TowerSelectionPanel extends GCompound implements MouseInteract {
    private static final int PANEL_WIDTH = 600; // Increased width for horizontal layout
    private static final int PANEL_HEIGHT = 100; // Reduced height since we're scrolling horizontally
    private static final int TOWER_ICON_SIZE = 40;
    private static final int TOWER_SPACING = 10;
    private static final int SCROLL_SPEED = 10;

    private List<TowerIcon> towerIcons;
    private GRect panelBackground;
    private GRect scrollLeftButton; 
    private GRect scrollRightButton;
    private double scrollOffset;
    private double maxScrollOffset;
    private Tower draggingTower;

    public TowerSelectionPanel() {
        towerIcons = new ArrayList<>();
        scrollOffset = 0;

        //  panel background
        panelBackground = new GRect(PANEL_WIDTH, PANEL_HEIGHT);
        panelBackground.setFilled(true);
        panelBackground.setFillColor(Color.LIGHT_GRAY);
        add(panelBackground);

        // scroll buttons 
        scrollLeftButton = new GRect(20, 20);
        scrollLeftButton.setFilled(true);
        scrollLeftButton.setFillColor(Color.GRAY);
        GLabel leftArrow = new GLabel("←");
        leftArrow.setFont("Arial-16");
        leftArrow.setLocation(
            scrollLeftButton.getX() + (scrollLeftButton.getWidth() - leftArrow.getWidth()) / 2,
            scrollLeftButton.getY() + (scrollLeftButton.getHeight() + leftArrow.getHeight()) / 2
        );
        add(scrollLeftButton, 5, (PANEL_HEIGHT - scrollLeftButton.getHeight()) / 2);
        add(leftArrow);

        scrollRightButton = new GRect(20, 20);
        scrollRightButton.setFilled(true);
        scrollRightButton.setFillColor(Color.GRAY);
        GLabel rightArrow = new GLabel("→");
        rightArrow.setFont("Arial-16");
        rightArrow.setLocation(
            scrollRightButton.getX() + (scrollRightButton.getWidth() - rightArrow.getWidth()) / 2,
            scrollRightButton.getY() + (scrollRightButton.getHeight() + rightArrow.getHeight()) / 2
        );
        add(scrollRightButton, PANEL_WIDTH - scrollRightButton.getWidth() - 5, (PANEL_HEIGHT - scrollRightButton.getHeight()) / 2);
        add(rightArrow);

        // Add tower icons
        addTowerIcon(new MeleeTower(), "Melee Tower");
        addTowerIcon(new RangedTower(), "Ranged Tower");

        // Calculate the maximum scroll offset 
        int totalWidth = towerIcons.size() * (TOWER_ICON_SIZE + TOWER_SPACING) + TOWER_SPACING;
        maxScrollOffset = Math.max(0, totalWidth - PANEL_WIDTH + 50); // Adjust for padding
    }

    private void addTowerIcon(Tower tower, String labelText) {
        TowerIcon icon = new TowerIcon(tower, labelText);
        int index = towerIcons.size();
        double xPos = TOWER_SPACING + index * (TOWER_ICON_SIZE + TOWER_SPACING);
        icon.setLocation(xPos, (PANEL_HEIGHT - TOWER_ICON_SIZE) / 2); // Center vertically in the panel
        towerIcons.add(icon);
        add(icon);
    }

    public void scroll(double delta) {
        scrollOffset = Math.max(0, Math.min(maxScrollOffset, scrollOffset + delta));
        updateTowerPositions();
    }

    private void updateTowerPositions() {
        for (int i = 0; i < towerIcons.size(); i++) {
            TowerIcon icon = towerIcons.get(i);
            double baseX = TOWER_SPACING + i * (TOWER_ICON_SIZE + TOWER_SPACING);
            icon.setLocation(baseX - scrollOffset, icon.getY()); // Update X position based on scrollOffset
        }
    }

    @Override
    public void onPress(MouseEvent e) {
        GPoint clickPoint = new GPoint(e.getX() - getX(), e.getY() - getY());
        if (scrollLeftButton.contains(clickPoint)) {
            scroll(-SCROLL_SPEED); // Scroll left
        } else if (scrollRightButton.contains(clickPoint)) {
            scroll(SCROLL_SPEED); // Scroll right
        } else {
            for (TowerIcon icon : towerIcons) {
                if (icon.contains(clickPoint)) {
                    // Create a new tower instance for dragging
                    Tower newTower = icon.createTower();
                    if (Character.getInstance().subtractBalance(newTower.getCost())) {
                        draggingTower = newTower;
                        draggingTower.setLocation(e.getX(), e.getY());
                        GardenScreen.getInstance().add(draggingTower);
                        MouseManager.setSelectedObject(draggingTower);
                        draggingTower.onPress(e);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void onDrag(MouseEvent e) {
        if (draggingTower != null) {
            draggingTower.onDrag(e);
        }
    }

    @Override
    public void onRelease(MouseEvent e) {
        if (draggingTower != null) {
            draggingTower.onRelease(e);
            draggingTower = null;
            MouseManager.setSelectedObject(null);
        }
    }

    @Override
    public void onHover(MouseEvent e) {
        // Optiona to add hover effects if desired
    }

    @Override
    public void stopHover() {
        // Optional to add reset hover effects if desired
    }

    // Inner class to represent a tower icon in the panel
    private class TowerIcon extends GCompound {
        private Tower towerTemplate;
        private GImage iconImage;
        private GLabel label;

        public TowerIcon(Tower tower, String labelText) {
            this.towerTemplate = tower;

            //  the tower icon image
            iconImage = new GImage(tower.toPath());
            iconImage.setSize(TOWER_ICON_SIZE, TOWER_ICON_SIZE);
            add(iconImage);

            // the label below the icon for horizontal layout
            label = new GLabel(labelText);
            label.setFont("Arial-12");
            label.setLocation((TOWER_ICON_SIZE - label.getWidth()) / 2, TOWER_ICON_SIZE + label.getHeight());
            add(label);
        }

        public Tower createTower() {
            if (towerTemplate instanceof MeleeTower) {
                return new MeleeTower();
            } else if (towerTemplate instanceof RangedTower) {
                return new RangedTower();
            }
            return null;
        }
    }
}