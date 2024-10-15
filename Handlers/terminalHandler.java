package Handlers;

import javax.swing.*;

import Processors.CommandProcessor;

import java.awt.*;
import java.awt.event.*;

public class terminalHandler extends JTextArea {

    private int immutableLength = 2;
    private String lastCommand = "";

    public terminalHandler(JPanel parentPanel) {
        this.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight()));
        this.setBackground(Color.GREEN);
        this.setOpaque(true);
        this.setText("> ");
        this.setCaretPosition(immutableLength);
        

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

    public void handleKeyPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume(); 
            captureAndExecuteCommand();
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (getCaretPosition() <= immutableLength) {
                e.consume(); 
            }
        } else if (getCaretPosition() < immutableLength) {
            setCaretPosition(immutableLength);
        }
    }

    private void captureAndExecuteCommand() {

        String fulltext = this.getText();
        String currentCommand = fulltext.substring(immutableLength).trim();

        lastCommand = currentCommand;

        executeCommand(currentCommand);

        append("\n> ");
       setImmutableLength();
    }

    public void executeCommand(String command) {
        System.out.println("Executed command: " + command);
        CommandProcessor cp = new CommandProcessor(this);
        cp.process(command);
    }

    public void setImmutableLength() {
        immutableLength = this.getDocument().getLength();
    }

    public void addToTerminal(String text) {
        this.append("\n" + text);
        setImmutableLength();
    }

    public void clear() {
        SwingUtilities.invokeLater(() -> {
            this.setText("> ");
            immutableLength = 2;
            this.setCaretPosition(immutableLength);
        });
    }
}
