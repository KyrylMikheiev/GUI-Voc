package src.ui.screens.learning;

import javax.swing.*;
import java.awt.*;

import src.App;
import src.ui.helper.*;
import src.ui.screens._BaseScreen;

public class LearningSelection extends LessonSelector {

    public LearningSelection() {
        super();
    }

    @Override
    public void execute(String lesson) {
        App.switchScreen(new LearningView(lesson));
    }
}