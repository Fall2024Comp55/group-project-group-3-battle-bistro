import UI.GameScreen;

public class BattleBistro {

    public static void main(String[] args) {
        GameScreen gameScreen = new GameScreen();
        gameScreen.setInstance(gameScreen);
        gameScreen.start();
    }
   
}
