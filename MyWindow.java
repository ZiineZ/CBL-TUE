import javax.swing.*;
import Handlers.*;
import java.awt.*;


public class MyWindow extends JFrame {

    final JPanel visualgui = new JPanel();
    final JPanel terminalgui = new JPanel();
    final JPanel scoregui = new JPanel();

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
        final int margin = 3;

        visualgui.setBounds(0+margin,0+margin,(int)(frame.getWidth()/1.5)-margin,(frame.getHeight()/2) - margin);
        terminalgui.setBounds(0+margin, (frame.getHeight() / 2) + margin, (int) (frame.getWidth() * 0.6667) - margin, (frame.getHeight() / 2) - 11*margin);
        scoregui.setBounds((int) (frame.getWidth() * 0.6667)+margin, 0 + margin, (int) (frame.getWidth() * 0.3334) - 2*margin, frame.getHeight() - 11*margin);

        visualgui.setBackground(Color.RED);
        terminalgui.setBackground(Color.GREEN);
        scoregui.setBackground(Color.BLUE);

        visualgui.setVisible(true);
        terminalgui.setVisible(true);
        scoregui.setVisible(true);


        frame.add(visualgui);
        frame.add(terminalgui);
        frame.add(scoregui);

        addHandlers();
    }

    void addHandlers() {
        terminalgui.add(new terminalHandler(terminalgui));
        scoregui.add(new ScoreHandler(scoregui));
    }
    
}
