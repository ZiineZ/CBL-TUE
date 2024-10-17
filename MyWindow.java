import javax.swing.*;
import Handlers.*;
import java.awt.*;


public class MyWindow extends JFrame {

    final JPanel visualgui = new JPanel();
    final JPanel terminalgui = new JPanel();
    final JPanel scoregui = new JPanel();

    private ScoreHandler scoreHandler;

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

        visualgui.setBackground(Color.RED);
        terminalgui.setBackground(Color.GREEN);
        scoregui.setBackground(Color.BLUE);

        leftPanel.add(visualgui);
        leftPanel.add(terminalgui);

        int frameWidth = frame.getWidth();
        scoregui.setPreferredSize(new Dimension(frameWidth / 3, frame.getHeight()));

        frame.add(leftPanel, BorderLayout.CENTER);
        frame.add(scoregui, BorderLayout.EAST);

        scoreHandler = new ScoreHandler(scoregui);

        addHandlers();
    }

    void addHandlers() {

        terminalgui.add(new terminalHandler(terminalgui));
        new ScoreHandler(scoregui);

        JButton fixOxygenButton = new JButton("Fix Oxygen Pipe");
        fixOxygenButton.addActionListener(e -> fixOxygenPipe());
        terminalgui.add(fixOxygenButton);
    }

    void fixOxygenPipe() {
        System.out.println("Oxygen pipe fixed!");
        scoreHandler.scoreCount(100);
    }
    
}
