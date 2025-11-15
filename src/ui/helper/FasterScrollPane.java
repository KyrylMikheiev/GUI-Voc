package src.ui.helper;

import javax.swing.*;

public class FasterScrollPane extends JScrollPane {
    private static final int SCROLL_INCREMENT = 20;

    public FasterScrollPane(JComponent component) {
        super(component);
        getVerticalScrollBar().setUnitIncrement(SCROLL_INCREMENT);
        getHorizontalScrollBar().setUnitIncrement(SCROLL_INCREMENT);
    }
}
