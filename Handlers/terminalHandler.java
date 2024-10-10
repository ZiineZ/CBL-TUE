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

        String command = this.getText();
        if (this.getText().isEmpty()) {
            this.setText(">");

            else if (command.charAt(0) != '>') {
                this.insert("> ", 0);
            }
    }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("Enter key pressed!");
            // Add your logic here
        }
    }

    public void maintainCommand(){

    }

    public void executeCommand(String command) {

    }
}
