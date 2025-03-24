package ElvinCode;

import javax.swing.SwingUtilities;


public class GameMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameUI::new);
    }
}
