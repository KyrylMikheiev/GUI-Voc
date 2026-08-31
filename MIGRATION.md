# Swing → JavaFX (FXML) Migration

Status: **complete**. Every screen is on JavaFX with FXML. The Swing tree is
untouched and still compiles, so the old UI can be run for comparison until it
is deleted.

This is a port, not a redesign: screens keep the behaviour they had, including
the parts that were unfinished (ForgotPassword's Submit does nothing,
TextChecker and Search are empty, TestView does not check answers).

## Running

```bash
./mvnw javafx:run                 # JavaFX, light theme
./mvnw javafx:run -Dvt.theme=dark # JavaFX, dark theme
./scripts/run-fx.sh [dark]        # same, plus submodule update
./scripts/run.sh                  # the original Swing app
```

Maven and the JavaFX SDK are downloaded automatically by `mvnw`; nothing has to
be installed by hand. (The JDK itself no longer bundles JavaFX, which is why the
raw-`javac` scripts cannot build the new UI.)

## What exists

| Swing | JavaFX | Notes |
|---|---|---|
| `src/Main.java` | `src/fx/FxMain.java` | `Application` owns the lifecycle |
| `src/App.java` | `src/fx/FxApp.java` | same static navigation facade |
| `src/ui/WindowManager.java` | `src/fx/ui/FxWindowManager.java` | `Stage` + one `Scene` |
| `src/ui/screens/_BaseScreen.java` | `src/fx/ui/FxScreen.java` | screens are FXML controllers |
| `src/ui/ColorManager.java` | `src/fx/ui/ThemeManager.java` + `*.css` | stylesheet swap |
| `src/ui/NavBar.java` | `src/fx/ui/NavBar.fxml` + `FxNavBar.java` | 240 lines → ~60 lines XML |
| `src/ui/helper/LessonSelector.java` | `src/fx/ui/LessonSelectorScreen.java` + `.fxml` | shared lesson picker |
| `src/ui/helper/LessonRoster.java` | folded into `TestSelection.fxml` | two ListViews |
| scattered `new ImageIcon(...)` | `src/fx/ui/Images.java` | cached, scaled by view |
| duplicated lesson sorting | `src/fx/ui/Lessons.java` | one sorted list |

Every screen has an `FXML` + controller pair under `src/fx/screens/`, mirroring
the old `src/ui/screens/` layout: `StartPage`, `auth/` (Login, SignUp,
ForgotPassword, Verification), `learning/` (LearningSelection, LearningView,
LibraryView, VocabView), `test/` (TestSelection, TestView, TextChecker),
`settings/` (Settings, Credits, PrivacyStatement), `games/` (GameSelection,
MemorySelection, MemoryMain) and `Search`.

## Checking your work

`FxmlSmokeTest` loads every screen in one pass. FXML binds at runtime, so a bad
`fx:id` or a handler name that does not exist compiles fine and only fails when
the screen is opened:

```bash
./mvnw javafx:run -DmainClass=src.fx.ui.FxmlSmokeTest   # all 22 views
./mvnw javafx:run -DmainClass=src.fx.ui.Preview -Dscreen=LearningView
```

Add each new screen to `FxmlSmokeTest.SCREENS` and `Preview`.

## How to port the next screen

1. Add `<name>.fxml` under `src/fx/screens/` describing the layout.
2. Add `<name>Screen.java` extending `FxScreen`, returning that path from
   `fxmlPath()`. `@FXML` fields bind by `fx:id`; `onAction="#method"` binds
   handlers. Put per-node icon wiring in `initialize()`.
3. Replace the `notYetMigrated(...)` call in the screen that navigates to it
   with `FxApp.switchScreen(new <name>Screen())`.

### Translation rules

- **Layout.** `BorderLayout` → `BorderPane`; `GridLayout(1, n)` → `GridPane`
  with `percentWidth` columns; `GridLayout(n, 1)` → `VBox` with
  `VBox.vgrow="ALWAYS"` and `maxWidth="Infinity"`; `BoxLayout` → `VBox`/`HBox`;
  `GridBagLayout` → `GridPane`; `FlowLayout` → `FlowPane`.
- **Colours.** Never set colours in Java. Attach a style class and define it in
  `common.css` using the `-vt-*` variables, so both themes follow.
- **Hover / click.** Delete the `MouseAdapter` blocks that call
  `setBackground(ColorManager.buttonHover())`; `.app-button:hover` and
  `:pressed` already cover them.
- **`ResponsiveBorder`.** Its fraction-of-parent insets are what percentage
  `GridPane` constraints and `maxWidth="Infinity"` do natively — use those
  rather than porting the class.
- **Placeholders.** `PlaceholderTextField` / `PlaceholderPasswordField` are not
  needed: use `promptText` on `TextField` / `PasswordField`.
- **`FasterScrollPane`.** Not needed; `ScrollPane` scrolls at a sensible rate.
- **`CustomProgressBarUI`.** Replaced by the `.progress-bar` rules in
  `common.css`.
- **Threading.** `SwingUtilities.invokeLater` → `Platform.runLater`.
  `javax.swing.Timer` → `Timeline` / `AnimationTimer`.
- **Dialogs.** `JOptionPane` → `Alert`.
- **Tables/lists.** `JTable` → `TableView`, `JList` → `ListView`,
  `JComboBox` → `ComboBox`, `JCheckBox` → `CheckBox`.

## Changes made to existing code

Two edits outside the new `src/fx/` tree, both needed so one `AuthManager` can
serve both UIs:

- `src/auth/AuthNavigator.java` (new) — navigation callback interface.
- `src/auth/AuthManager.java` — the three `App.setFreshState(new ...)` calls now
  go through `AuthNavigator`; it no longer imports Swing screens.
- `src/App.java` — registers a navigator that keeps Swing behaviour identical.

## Known issues, pre-existing

`src/ui/screens/games/memory/GameM.java` and
`src/ui/screens/games/fallingWords/FallingWordsGame.java` both declare
`package minigames` while living under `src/ui/screens/games/`, and `GameM` does
not compile (no `actionPerformed`, imports a `src.Main` it cannot see). Nothing
references either file. The old `run.sh` never caught this because its
`src/**/*.java` glob does not recurse that far. They are excluded in `pom.xml`;
delete them or move them into a real package to re-include.

That same glob means `scripts/run.sh` never compiled `src/ui/screens/**` at all —
on a clean checkout it fails with ~42 errors. `./mvnw compile` builds every
source file, Swing tree included.

## Bugs fixed in passing

Where the Swing code could not be ported as written, the fix is noted in the
class comment:

- `LibraryView` hardcoded 44 as the lesson count, so later lessons were
  unreachable and the label showed an index rather than a lesson name; its
  previous-button wrapped to `size() - 2`, skipping the last lesson.
- `LearningView`'s step-back used `List.remove(Integer)`, which removes by
  index, and threw once a vocabulary ID exceeded the list size. It also indexed
  into an empty list when a lesson had no vocabulary.
- `LessonSelector` added its button panel to the screen twice, so the list
  rendered above its own scrolling copy.
- `TestView` built its translation panel twice, adding both to
  `BorderLayout.WEST`, so the first was discarded.
- `MemoryMain` placed pairs by drawing random slots until two were free, and
  compared strings with `==`.

## Next steps

The Swing tree under `src/ui/` and `src/Main.java` can be deleted once you are
satisfied with the JavaFX version; nothing outside it depends on it any more.
`src/api/` and `src/auth/` are shared and stay as they are.
