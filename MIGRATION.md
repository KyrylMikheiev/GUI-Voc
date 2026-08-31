# Swing → JavaFX (FXML) Migration

Status: **foundation spike**. The build, the navigation shell, theming and one
screen (StartPage) are on JavaFX. The Swing UI is untouched and still runs, so
both front-ends coexist while the remaining screens are ported.

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
| `src/ui/screens/StartPage.java` | `src/fx/screens/StartPage.fxml` + `StartPageScreen.java` | reference screen |
| scattered `new ImageIcon(...)` | `src/fx/ui/Images.java` | cached, scaled by view |

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

## Remaining screens

`auth/` (Login, SignUp, ForgotPassword, Verification) · `learning/`
(LearningSelection, LearningView, LibraryView, VocabView) · `test/`
(TestSelection, TestView, TextChecker) · `games/` (GameSelection, MemoryMain,
MemorySelection) · `settings/` (Settings, Credits, PrivacyStatement) · `Search`
· helpers `LessonSelector` and `LessonRoster`.

`LearningView` (501 lines) and `TestSelection` (269) are the largest; the
`api/` and `auth/` packages need no UI work.
