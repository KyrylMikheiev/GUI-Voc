package src.ui.helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderPasswordField extends JPasswordField implements FocusListener {

    private String placeholder;
    private Color placeholderColor;

    public PlaceholderPasswordField(String placeholder, Color placeholderColor) {
        this.placeholder = placeholder;
        this.placeholderColor = placeholderColor;
        addFocusListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // If the password field is empty, paint the placeholder text
        if (getPassword().length == 0) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(placeholderColor);
            g2d.setFont(getFont().deriveFont(Font.ITALIC));
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int x = getInsets().left;
            int y = (getHeight() - g.getFontMetrics().getHeight()) / 2 + g.getFontMetrics().getAscent();
            g2d.drawString(placeholder, x, y);
            g2d.dispose();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        // Repaint when the password field gains focus
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        // Repaint when the password field loses focus
        repaint();
    }
}
