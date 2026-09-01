package de.vocabtrainer.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads icons from the classpath.
 *
 * <p>Images are cached, and scaling is a view property rather than a new
 * bitmap. Loading goes through the classpath rather than the filesystem, so it
 * does not depend on the working directory and keeps working from a jar.
 */
public final class Images {

    private static final String IMAGE_PATH = "/de/vocabtrainer/images/";
    private static final Map<String, Image> CACHE = new HashMap<>();

    private Images() {
    }

    /** Loads an image by file name, e.g. {@code "logo.png"}. */
    public static Image load(String fileName) {
        // containsKey rather than computeIfAbsent: a missing image caches as
        // null, and computeIfAbsent would retry it on every call.
        if (CACHE.containsKey(fileName)) {
            return CACHE.get(fileName);
        }

        Image image = null;
        try (InputStream in = Images.class.getResourceAsStream(IMAGE_PATH + fileName)) {
            if (in == null) {
                System.err.println("Missing image resource: " + IMAGE_PATH + fileName);
            } else {
                image = new Image(in);
            }
        } catch (Exception e) {
            System.err.println("Could not load image " + fileName + ": " + e);
        }

        CACHE.put(fileName, image);
        return image;
    }

    /**
     * Returns an ImageView scaled to fit the given box, preserving the aspect
     * ratio.
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
