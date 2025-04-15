package Tower;

import acm.graphics.GImage;

import java.lang.reflect.InvocationTargetException;

public enum Towers {
    MELEE_TOWER(true, TowerType.MELEE, MeleeTower.class),
    RANGED_TOWER(false, TowerType.RANGED, RangedTower.class);

    private final TowerType towerType;
    private final Class<? extends Tower> towerClass;
    private boolean unlocked;

    Towers(boolean unlocked, TowerType towerType, Class<? extends Tower> towerClass) {
        this.unlocked = unlocked;
        this.towerType = towerType;
        this.towerClass = towerClass;
    }

    public GImage getgImage() {
        try {
            return (GImage) towerClass.getMethod("getgImage").invoke(createTower());
        } catch (Exception e) {
            throw new RuntimeException("Error getting gImage for " + towerClass.getSimpleName(), e);
        }
    }

    public Integer getCost() {
        try {
            return (Integer) towerClass.getMethod("getCost").invoke(createTower());
        } catch (Exception e) {
            throw new RuntimeException("Error getting cost for " + towerClass.getSimpleName(), e);
        }
    }

    public Tower createTower() {
        try {
            return towerClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("Error creating tower of type " + towerClass.getSimpleName(), e);
        }
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public Class<? extends Tower> getTowerClass() {
        return towerClass;
    }

    public static enum TowerType {
        MELEE,
        RANGED
    }
}
