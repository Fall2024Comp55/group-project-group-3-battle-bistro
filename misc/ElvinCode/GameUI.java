package ElvinCode;

import javax.swing.*;
import java.awt.*;


public class GameUI extends JFrame {
    public GameUI() {
        setTitle("Tower Defense Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new GamePanel(), BorderLayout.CENTER);
        setVisible(true);
    }
}
