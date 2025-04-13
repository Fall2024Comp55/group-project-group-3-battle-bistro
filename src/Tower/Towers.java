package Tower;

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
