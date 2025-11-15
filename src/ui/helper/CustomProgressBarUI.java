package src.ui.helper;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class CustomProgressBarUI extends BasicProgressBarUI {
    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        // Get the dimensions of the progress bar
        Insets b = progressBar.getInsets(); // Border Insets
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

        // Calculate the width of the filled area
        int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

        // Set the color of the filled area
        g.setColor(Color.GREEN);

        // Fill the progress bar with the specified color
        g.fillRect(b.left, b.top, amountFull, barRectHeight);

        String text = String.valueOf((int) ((double) progressBar.getValue() / progressBar.getMaximum() * 100)) + "%";
        
        Font originalFont = g.getFont();
        Font largerFont = originalFont.deriveFont(originalFont.getSize() * 1.5f); // Adjust the multiplier for the desired size    
        g.setFont(largerFont);
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();

        // Enable anti-aliasing for smoother text rendering
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int x = (barRectWidth - textWidth) / 2 + b.left;
        int y = (barRectHeight - textHeight) / 2 + fontMetrics.getAscent() + b.top;

        g2d.setColor(progressBar.getForeground());
        g2d.drawString(text, x, y);
    }
}