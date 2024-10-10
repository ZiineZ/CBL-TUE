package Handlers;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;

public class terminalHandler extends JTextArea implements ActionListener, FocusListener {

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Performed!");
    }

    @Override
    public void focusGained(FocusEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'focusGained'");
    }

    @Override
    public void focusLost(FocusEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'focusLost'");
    }

}
