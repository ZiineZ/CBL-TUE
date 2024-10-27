import javax.swing.*;
import Handlers.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MyWindow extends JFrame {

    final JPanel terminalgui = new JPanel();
    final JPanel scoregui = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(new ImageIcon("Assets/metalpanelrotated.png").getImage(), 0, 0, getWidth(), getHeight(), this);
        };
    };

    MyWindow() {
        this.setTitle("TTC-300");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(960, 540);
        this.getContentPane().setBackground(new Color(17, 17, 17));

        initPanels(this);
        this.setVisible(true);
        
    }

    void initPanels(JFrame frame) {
        frame.setLayout(new BorderLayout(5, 5));

        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        leftPanel.setBackground(new Color(17,17,17));
        visualHandler visualgui = new visualHandler();

        leftPanel.add(visualgui);
        leftPanel.add(terminalgui);

        int frameWidth = frame.getWidth();
        scoregui.setPreferredSize(new Dimension(frameWidth / 3, frame.getHeight()));

        frame.add(leftPanel, BorderLayout.CENTER);
        frame.add(scoregui, BorderLayout.EAST);

        addHandlers();
    }

    void addHandlers() {
        terminalgui.setLayout(new BorderLayout()); // Set layout for proper stacking
        new terminalHandler(terminalgui);
        new ScoreHandler(scoregui);
    }
}
