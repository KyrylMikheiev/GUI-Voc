package src;

import javax.swing.JPanel;

public class MainMenu {

    private int clickedElement = 0;
    private JPanel body;
    private JPanel contentPane;

    public int run(JPanel content)
    {
        // # create main menu
        body = content;
        contentPane = new JPanel();
        //create elements and add them to the contentPane



        body.add(contentPane);

        return clickedElement;
    }

}
