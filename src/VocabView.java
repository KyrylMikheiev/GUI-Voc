package src;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Font;
import VocabParsing.Vocab;


public class VocabView {
    public VocabView(JPanel content, Vocab v) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(4, 2, 20, 20));
        bodyPanel.setBackground(Main.BodyColor);

        JLabel title = new JLabel(v.getBasicForm());
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        title.setForeground(Main.TextColor);

        JLabel translation = new JLabel(v.getGerman().toString().replace("[", "").replace("]", ""));
        translation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        translation.setForeground(Main.TextColor);

        bodyPanel.add(title);
        bodyPanel.add(translation);

        content.add(bodyPanel);
    }
}
