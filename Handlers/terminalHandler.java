package Handlers;

import javax.swing.*;
import Processors.CommandProcessor;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;

public class terminalHandler extends JTextArea {

    private int immutableLength = 2;
    CommandProcessor cp = new CommandProcessor(this);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img = new ImageIcon("Assets/scanlines.png").getImage();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        g2d.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    public terminalHandler(JPanel parentPanel) {
        this.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight()));
        this.setBackground(new Color(34, 34, 34));
        this.setForeground(new Color(57, 255, 20));
        this.setOpaque(true);
        this.setText("> ");
        this.setCaretPosition(immutableLength);
        
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Linebeam.ttf"));
            customFont = customFont.deriveFont(Font.PLAIN, 14); 
            this.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // Create a JScrollPane and add the JTextArea
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight()));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());

        // Create the JLayeredPane and the JLabel for scanlines
        JLayeredPane scanlinesLayer = new JLayeredPane();
        scanlinesLayer.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight()));

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

        if ((e.isControlDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_A) {
            e.consume();
            return;
        }

        if ((e.isControlDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            e.consume();
            return; 
        }

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

        executeCommand(currentCommand);
        append("\n> ");
        setImmutableLength();
    }

    public void executeCommand(String command) {
        System.out.println("Executed command: " + command);
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
