package src.fx.ui;

import java.util.ArrayList;
import java.util.List;

import VocabAPI.VocabParser;
import VocabAPI.WordTypes.Vocab;

/**
 * Lesson lookup shared by the screens that offer a lesson choice.
 *
 * <p>Used by the lesson picker, the test roster and the library view, which
 * would otherwise each build and sort this list themselves.
 */
public final class Lessons {

    private static List<String> cached;

    private Lessons() {
    }

    /** All lesson names, numeric ones in numeric order, cached after first use. */
    public static List<String> all() {
        if (cached == null) {
            List<String> lessons = new ArrayList<>();
            for (Vocab vocab : VocabParser.getAllVocabs()) {
                if (!lessons.contains(vocab.getLesson())) {
                    lessons.add(vocab.getLesson());
                }
            }
            lessons.sort(Lessons::compare);
            cached = lessons;
        }
        return cached;
    }

    /** Numeric lessons sort numerically, everything else case-insensitively. */
    private static int compare(String a, String b) {
        if (a.matches("\\d+") && b.matches("\\d+")) {
            return Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
        }
        return a.compareToIgnoreCase(b);
    }
}
