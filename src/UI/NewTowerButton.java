package UI;

import Character.Character;
import Screen.GardenScreen;
import Tower.Tower;
import Tower.Towers;
import Utils.MouseManager;

import java.awt.event.MouseEvent;

public class NewTowerButton extends Button {
    private Towers tower;

    public NewTowerButton(Towers tower) {
        super(tower, 20);
        this.tower = tower;
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