package src.ui.screens.games.memory;

import javax.swing.*;
import java.awt.*;

import src.App;
import src.ui.helper.*;
import src.ui.screens._BaseScreen;

public class MemorySelection extends LessonSelector {

    public MemorySelection() {
        super();
    }

    @Override
    public void execute(String lesson) {
        App.switchScreen(new MemoryMain(lesson));
    }
}