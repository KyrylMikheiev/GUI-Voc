package src;

import javax.swing.JPanel;

public class MemorySelection extends LessonSelector {
    //private JPanel content;
    private Main main;

    public MemorySelection(JPanel content, Main main) {
        super(content, main);
        //this.content = content;
        this.main = main;
    }
    @Override
    public void execute(String lesson) {
        System.out.println("starting game");
        main.newGameM(lesson);
    }
}