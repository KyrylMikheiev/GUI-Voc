package src.ui.screens.settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.ui.ColorManager;
import src.ui.screens._BaseScreen;
import src.ui.helper.*;

public class PrivacyStatement extends _BaseScreen {
    public PrivacyStatement() {
        super();
    }

    @Override
    public JPanel createUI() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(ColorManager.bodyPrimary());

        JLabel title = new JLabel("Datenschutzerklärung");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        title.setForeground(ColorManager.text());
        bodyPanel.add(title);

        JLabel statement = new JLabel("Wir klauen und verkaufen alle Ihre Daten weil wir die DSGVO nicht akzeptieren.");
        statement.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        statement.setForeground(ColorManager.text());
        bodyPanel.add(statement);

        return bodyPanel;
    }
}