package Handlers;

import javax.swing.*;
import Processors.CommandProcessor;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
/**
 * this class sets the working and looks of the terminal you see at the bottom left of the game.
 */
public class terminalHandler extends JTextArea {

    private int immutableLength = 0;
    CommandProcessor cp = new CommandProcessor(this);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img = new ImageIcon("Assets/scanlines.png").getImage();
        Graphics2D g2d = (Graphics2D) g;
        int imgHeight = img.getHeight(this);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));

        for (int y = 0; y < getHeight(); y += imgHeight) {
            g2d.drawImage(img, 0, y, this);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    /**
     * initializes standard values for the terminal such as: font, keylisteners and starting text.
     */
    public terminalHandler() {
        //this.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight()));
        this.setBackground(new Color(34, 34, 34));
        this.setForeground(new Color(57, 255, 20));
        this.setVisible(true);
        
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Linebeam.ttf"));
            customFont = customFont.deriveFont(Font.PLAIN, 14); 
            this.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });

        this.setText("Welcome to TTC-300, Operator!\n" +
                "You are in charge of a new experimental mining vehicle.\n" +
                "Take a look at the lamp dashboard above.\n" +
                "If a lamp lights up, it indicates a problem!\n" +
                "To resolve issues, specific commands must be entered.\n" +
                "There are three types of problems: Temperature, Ventilation and Fuel!\n" +
                "The fourth lamp indicates ore mining, which can earn you extra points!\n" +
                "To see the commands you need to input, type: ex: 'system.help.mine()'\n" +
                "Or for example: 'system.help.temperature()' etc.\n" +
                "To see the current state of your ships type: system.getstatus()\n" +
                "Beware: when three problems are active, pray for your survival!\n" +
                "To start the game type: 'start()'\n" +
                "\n> ");
        setImmutableLength();
        this.setCaretPosition(immutableLength);

    }
    /**
     * this handles every keypress which is excercised onto the console. this makes the console act like a console instead of a textbox.
     */
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

    /**
     * captures the command inputted by the user.
     */
    private void captureAndExecuteCommand() {
        String fulltext = this.getText();
        String currentCommand = fulltext.substring(immutableLength).trim();

        executeCommand(currentCommand);
        append("\n> ");
        setImmutableLength();
    }

    /**
     * executes the command and forwards it to the commandprocessor.
     * @param command
     */
    public void executeCommand(String command) {
        System.out.println("Executed command: " + command);
        cp.process(command);
    }

    public void setImmutableLength() {
        immutableLength = this.getDocument().getLength();
    }

    public void addToTerminal(String text) {
        this.append("\n" + text + "\n");
        setImmutableLength();
    }

    /**
     * clears the terminal screen.
     */
    public void clear() {
        SwingUtilities.invokeLater(() -> {
            this.setText("> ");
            immutableLength = 2;
            this.setCaretPosition(immutableLength);
        });
    }
}
