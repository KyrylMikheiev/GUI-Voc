package de.vocabtrainer.ui.screens.games.memory;

import de.vocabtrainer.App;
import de.vocabtrainer.ui.LessonSelector;

/** Lesson picker for the memory game. */
public class MemorySelection extends LessonSelector {

    @Override
    public void execute(String lesson) {
        App.switchScreen(new MemoryMain(lesson));
    }
}
