package src;

import javax.swing.JPanel;

public class LearningSelection extends LessonSelector {
    private Main main;

    public LearningSelection(JPanel content, Main main) {
        super(content, main);
        this.main = main;
    }
    @Override
    public void execute(String lesson) {
        main.newLearningView(lesson);
    }
}