package src.fx.screens.learning;

import src.fx.FxApp;
import src.fx.ui.LessonSelectorScreen;

/**
 * Lesson picker for the learning flow, replacing
 * {@link src.ui.screens.learning.LearningSelection}.
 */
public class LearningSelectionScreen extends LessonSelectorScreen {

    @Override
    public void execute(String lesson) {
        FxApp.switchScreen(new LearningViewScreen(lesson));
    }
}
