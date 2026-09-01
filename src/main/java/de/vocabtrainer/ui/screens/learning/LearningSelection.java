package de.vocabtrainer.ui.screens.learning;

import de.vocabtrainer.App;
import de.vocabtrainer.ui.LessonSelector;

/** Lesson picker for the learning flow. */
public class LearningSelection extends LessonSelector {

    @Override
    public void execute(String lesson) {
        App.switchScreen(new LearningView(lesson));
    }
}
