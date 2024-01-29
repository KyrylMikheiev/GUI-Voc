package src;


import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

public class PrivacyStatement {
    public PrivacyStatement(JPanel content) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(Main.BodyColor);

        JLabel title = new JLabel("Datenschutzerklärung");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        title.setForeground(Main.TextColor);
        bodyPanel.add(title);

        JLabel statement = new JLabel("Wir klauen und verkaufen alle Ihre Daten weil wir die DSGVO nicht akzeptieren.");
        statement.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        statement.setForeground(Main.TextColor);
        bodyPanel.add(statement);

        content.add(bodyPanel);
    }
}
