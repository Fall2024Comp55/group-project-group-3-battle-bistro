package UI;

import Character.Character;
import Screen.GardenScreen;
import Tower.Tower;
import Tower.Towers;
import Utils.MouseManager;
import acm.graphics.GLabel;

import java.awt.event.MouseEvent;

import static Screen.ProgramWindow.GLOBAL_COLOR;

public class NewTowerButton extends Button {
    private Towers tower;
    private GLabel cost;

    public NewTowerButton(Towers tower) {
        super(tower, 25);
        this.tower = tower;
        cost = new GLabel(String.valueOf(tower.getCost()) + "$");
        cost.setFont("Arial-12");
        cost.setColor(GLOBAL_COLOR);

        cost.setLocation(cost.getWidth() / 2 - cost.getWidth(), getHeight());
    }

    public void addCostLabel() {
        add(cost);
    }


    @Override
    public void onPress(MouseEvent e) {
        if (Character.getInstance().subtractBalance(tower.getCost())) {
            Tower newTower = tower.createTower();

            newTower.setLocation(e.getX(), e.getY());
            GardenScreen.getInstance().add(newTower);
            MouseManager.setSelectedObject(newTower);
            newTower.onPress(e);
        }
    }
}