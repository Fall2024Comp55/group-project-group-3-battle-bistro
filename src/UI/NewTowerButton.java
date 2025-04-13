package UI;

import Character.Character;
import Screen.GardenScreen;
import Tower.RangedTower;
import Tower.Tower;
import Utils.MouseManager;

import java.awt.event.MouseEvent;

public class NewTowerButton extends Button {
    private Tower tower;

    public NewTowerButton() {
    }

    @Override
    public void onPress(MouseEvent e) {
        tower = new RangedTower();
        if (Character.getInstance().subtractBalance(tower.getCost())) {

            tower.setLocation(e.getX(), e.getY());
            GardenScreen.getInstance().add(tower);
            MouseManager.setSelectedObject(tower);
            tower.onPress(e);
        } else {
            tower = null;
        }
    }

    @Override
    public void onDrag(MouseEvent e) {
        if (tower != null) {
            tower.onDrag(e);
        }
    }

    @Override
    public void onRelease(MouseEvent e) {
        if (tower != null) {
            tower.onRelease(e);
        }
    }
}