package Handlers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class terminalHandler extends JTextArea {

    public terminalHandler(JPanel parentPanel) {
        this.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight()));
        this.setBackground(Color.GREEN);
        this.setOpaque(true);
        this.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque vitae velit ex.");

        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight()));

        parentPanel.setLayout(new BorderLayout());
        parentPanel.add(scrollPane, BorderLayout.CENTER);

        parentPanel.revalidate();
        parentPanel.repaint();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    // Separate method to handle key presses
    public void handleKeyPress(KeyEvent e) {
        maintainCommand();
    }

    public void maintainCommand(){
        SwingUtilities.invokeLater(() -> {
            String command = this.getText();
            if (command.isEmpty() || command.length() == 1) {
                this.setText("> ");
            }
            else if (command.charAt(0) != '>' || command.charAt(1) != ' ') {
                    this.insert("> ", 0);
            }
        });
    }

    public void executeCommand(String command) {
        
    }
}
