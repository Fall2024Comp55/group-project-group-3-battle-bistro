package UI;

import Character.Player;
import Tower.MeleeTower;
import Tower.Tower;
import Utils.GameTick;
import Utils.MouseManager;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;

import java.awt.event.MouseEvent;

public class NewTowerButton extends Button {
    private static final int TOWER_COST = 5;
    private Tower tower;

    public NewTowerButton(String text) {
        super(text);
    }

    @Override
    public void onPress(MouseEvent e) {
        Player player = Player.getInstance();
        if (player.getMoney() >= TOWER_COST) {
     
            player.subtractMoney(TOWER_COST);
   
            tower = new MeleeTower();
            tower.setLocation(e.getX(), e.getY());
            GameScreen.getInstance().add(tower);
            MouseManager.setSelectedObject(tower);
            GameTick.TickManager.registerTickListener(tower);
            tower.onPress(e);
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
            
            GRectangle towerBounds = tower.getHitbox();
            boolean onPath = false;
            for (GPoint point : GameScreen.getPath().getPoints()) {
                GRectangle pointBounds = new GRectangle(point.getX(), point.getY(), 1, 1);
                if (towerBounds.intersects(pointBounds)) {
                    onPath = true;
                    break;
                }
            }

            if (onPath) {
               
                Player.getInstance().addMoney(TOWER_COST);
                GameScreen.getInstance().remove(tower);
            } else {
             
                tower.onRelease(e);
                GameTick.TickManager.registerTickListener(tower);
            }
            tower = null;
            MouseManager.setSelectedObject(null);
        }
    }
}