package src.fx.screens.games.memory;

import src.fx.FxApp;
import src.fx.ui.LessonSelectorScreen;

/** Lesson picker for the memory game. */
public class MemorySelectionScreen extends LessonSelectorScreen {

    @Override
    public void execute(String lesson) {
        FxApp.switchScreen(new MemoryMainScreen(lesson));
    }
}
