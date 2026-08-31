package src.fx.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * JavaFX counterpart of {@link src.ui.screens._BaseScreen}.
 *
 * <p>A screen is now a controller: the layout comes from an FXML file loaded by
 * {@link #getView()}, rather than from a createUI() method that assembles panels
 * in code. Subclasses declare which FXML to load and receive
 * {@link #onViewLoaded()} once the {@code @FXML} fields have been injected.
 *
 * <p>The Swing base class also owned repainting and re-applied colours on every
 * theme change; both are gone. CSS restyles the live scene graph, so the view is
 * built once and cached.
 */
public abstract class FxScreen {

    protected boolean keepInHistory = true;
    protected boolean hasNavbar = true;

    private Parent view;

    /**
     * FXML location, resolved against this class. Screens that build their view
     * in code instead override {@link #createView()}.
     */
    protected String fxmlPath() {
        return null;
    }

    /** Called after the FXML is loaded and controller fields are injected. */
    protected void onViewLoaded() {
    }

    /**
     * Class whose package {@link #fxmlPath()} is relative to. Defaults to the
     * concrete screen; a base class that owns a shared FXML overrides this.
     */
    protected Class<?> fxmlOwner() {
        return getClass();
    }

    /**
     * Builds the screen's view. The default loads {@link #fxmlPath()} with this
     * screen as the controller, so {@code @FXML} fields bind to it directly.
     */
    protected Parent createView() {
        String path = fxmlPath();
        if (path == null) {
            throw new IllegalStateException(
                    getClass().getName() + " must override fxmlPath() or createView()");
        }
        // Resolve against the class that declares fxmlPath(), not against
        // getClass(): a shared base screen such as LessonSelectorScreen keeps
        // its FXML in its own package, while getClass() is the subclass.
        java.net.URL location = fxmlOwner().getResource(path);
        if (location == null) {
            throw new IllegalStateException(
                    "FXML not found: " + path + " (relative to " + fxmlOwner().getName() + ")");
        }
        FXMLLoader loader = new FXMLLoader(location);
        loader.setController(this);
        try {
            return loader.load();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load FXML: " + path, e);
        }
    }

    /** Returns the view, building it on first access. */
    public final Parent getView() {
        if (view == null) {
            view = createView();
            onViewLoaded();
        }
        return view;
    }

    public void setKeepInHistory(boolean val) {
        keepInHistory = val;
    }

    public boolean storeInHistory() {
        return keepInHistory;
    }

    public void setHasNavbar(boolean val) {
        hasNavbar = val;
    }

    public boolean hasNavbar() {
        return hasNavbar;
    }
}
