package ElvinCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
    private ArrayList<Enemy> enemies;
    private ArrayList<Tower> towers;
    private Timer gameTimer;
    private Random random;
    private Tower selectedTower;
    private int[][] path = {
        {0, 100}, {100, 100}, {100, 200}, {200, 200}, {200, 300}, {300, 300}, {400, 300}, {500, 250}
    };

    public GamePanel() {
        enemies = new ArrayList<>();
        towers = new ArrayList<>();
        random = new Random();

     
        towers.add(new Tower(150, 150, 10, 50));
        towers.add(new Tower(250, 250, 15, 70));

        addMouseListener(this);
        addMouseMotionListener(this);

      
        gameTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateGame();
                    repaint();
                } catch (Exception ex) {
                    System.err.println("Error in game loop: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        gameTimer.setRepeats(true); // Ensure the timer continues running
        gameTimer.start();
    }

    private void updateGame() {
        if (random.nextInt(10) < 2) {
            enemies.add(new Enemy(20, 3));
        }

        for (Enemy enemy : enemies) {
            enemy.move();
        }

        for (Tower tower : towers) {
            tower.attack(enemies);
        }

        enemies.removeIf(enemy -> !enemy.isAlive());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.GREEN);

       
        g.setColor(Color.DARK_GRAY);
        ((Graphics2D) g).setStroke(new BasicStroke(5)); // Make path thicker
        for (int i = 0; i < path.length - 1; i++) {
            g.drawLine(path[i][0], path[i][1], path[i + 1][0], path[i + 1][1]);
        }

        
        g.setColor(Color.RED);
        for (Enemy enemy : enemies) {
            g.fillOval(enemy.getX(), enemy.getY(), 20, 20);
        }


        g.setColor(Color.BLUE);
        for (Tower tower : towers) {
            g.fillRect(tower.getX(), tower.getY(), 20, 20);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selectedTower != null) {
            selectedTower.setPosition(e.getX(), e.getY(), path);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (Tower tower : towers) {
            if (new Rectangle(tower.getX(), tower.getY(), 20, 20).contains(e.getPoint())) {
                selectedTower = tower;
                break;
            }
        }
    }

    @Override 
    public void mouseReleased(MouseEvent e) { selectedTower = null; }

    public void mouseMoved(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}
 
    public void mouseExited(MouseEvent e) {}
}
