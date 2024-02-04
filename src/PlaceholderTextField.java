package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextField extends JTextField implements FocusListener {

    private String placeholder;
    private Color placeholderColor;

    public PlaceholderTextField(String placeholder, Color placeholderColor) {
        this.placeholder = placeholder;
        this.placeholderColor = placeholderColor;
        addFocusListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // If the text field is empty, paint the placeholder text
        if (getText().isEmpty()) {
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
        // Repaint when the text field gains focus
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        // Repaint when the text field loses focus
        repaint();
    }

    public void setEchoChar(char c) {
        //setEchoChar(c);
    }

}
