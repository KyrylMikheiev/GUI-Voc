package de.vocabtrainer.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads icons from the {@code resources/images} directory.
 *
 * <p>Images are cached, and scaling is a view property rather than a new
 * bitmap.
 */
public final class Images {

    private static final String IMAGE_DIR = "resources/images/";
    private static final Map<String, Image> CACHE = new HashMap<>();

    private Images() {
    }

    /** Loads an image by file name, e.g. {@code "logo.png"}. */
    public static Image load(String fileName) {
        return CACHE.computeIfAbsent(fileName, name -> {
            File file = new File(IMAGE_DIR + name);
            if (!file.exists()) {
                System.err.println("Missing image resource: " + file.getPath());
                return null;
            }
            return new Image(file.toURI().toString());
        });
    }

    /**
     * Returns an ImageView scaled to fit the given box, preserving the aspect
     * ratio. Replaces {@code Image.getScaledInstance(w, h, SCALE_SMOOTH)}.
     */
    public static ImageView view(String fileName, double width, double height) {
        ImageView view = new ImageView(load(fileName));
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        return view;
    }
}
