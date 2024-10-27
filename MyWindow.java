import javax.swing.*;
import Handlers.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MyWindow extends JFrame {
;
    final JPanel terminalgui = new JPanel();
    final BackgroundPanel scoregui = new BackgroundPanel();


    MyWindow(){

        this.setTitle("TTC-300");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(960, 540);

        this.getContentPane().setBackground(Color.WHITE);

        initPanels(this);
        this.setVisible(true);
    }

    void initPanels(JFrame frame){
        
        frame.setLayout(new BorderLayout(5, 5));

        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        visualHandler visualgui = new visualHandler();

        scoregui.setBackgroundImage("Assets/Background.jpg");

        leftPanel.add(visualgui);
        leftPanel.add(terminalgui);

        int frameWidth = frame.getWidth();
        scoregui.setPreferredSize(new Dimension(frameWidth / 3, frame.getHeight()));

        frame.add(leftPanel, BorderLayout.CENTER);
        frame.add(scoregui, BorderLayout.EAST);


        addHandlers();
    }

    void addHandlers() {

        terminalgui.add(new terminalHandler(terminalgui));
        new ScoreHandler(scoregui);
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public void setBackgroundImage(String filePath) {
            try {
                backgroundImage = ImageIO.read(new File(filePath));
            } catch (IOException e) {
                System.out.println("Error loading background image: " + e.getMessage());
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    
}
