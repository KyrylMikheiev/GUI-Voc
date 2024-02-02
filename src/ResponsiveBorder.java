package src;
import javax.swing.border.Border;
import java.awt.*;

public class ResponsiveBorder implements Border {
    private double topFraction;
    private double leftFraction;
    private double bottomFraction;
    private double rightFraction;

    public ResponsiveBorder(double topFraction, double leftFraction, double bottomFraction, double rightFraction) {
        this.topFraction = topFraction;
        this.leftFraction = leftFraction;
        this.bottomFraction = bottomFraction;
        this.rightFraction = rightFraction;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        // Border doesn't need to be painted
    }

    @Override
    public Insets getBorderInsets(Component c) {
        Container parent = c.getParent();
        if (parent != null) {
            int top = (int) (parent.getHeight() * topFraction / 720);
            int left = (int) (parent.getWidth() * leftFraction / 1280);
            int bottom = (int) (parent.getHeight() * bottomFraction / 720);
            int right = (int) (parent.getWidth() * rightFraction / 1280);
            return new Insets(top, left, bottom, right);
        } else {
            return new Insets(0, 0, 0, 0);
        }
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
