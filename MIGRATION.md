# Swing → JavaFX (FXML) Migration

Done. The Swing UI has been removed; the app is JavaFX with FXML throughout.
This file is kept as a record of what moved where and why, and as a guide for
adding screens. See `README.md` for how to run the app.

The port kept the behaviour the screens had, including the parts that were
unfinished: ForgotPassword's Submit does nothing, TextChecker and Search are
empty, TestView does not check answers, and VocabView's gender switch is not
wired up.

## What moved where

Paths below are relative to `src/main/java/de/vocabtrainer/`; the FXML and CSS
sit at the matching path under `src/main/resources/de/vocabtrainer/`.

| Was | Is now | Notes |
|---|---|---|
| `src/Main.java` | `Main.java` | `Application` owns the lifecycle |
| `src/App.java` | `App.java` | same static navigation facade |
| `src/ui/WindowManager.java` | `ui/WindowManager.java` | `Stage` + one `Scene` |
| `src/ui/screens/_BaseScreen.java` | `ui/Screen.java` | screens are FXML controllers |
| `src/ui/ColorManager.java` | `ui/Theme.java` + `*.css` | stylesheet swap |
| `src/ui/NavBar.java` | `ui/NavBar.fxml` + `NavBar.java` | 240 lines → ~60 lines of XML |
| `src/ui/helper/LessonSelector.java` | `ui/LessonSelector.java` + `.fxml` | shared lesson picker |
| `src/ui/helper/LessonRoster.java` | folded into `TestSelection.fxml` | two ListViews |
| `src/ui/helper/ResponsiveBorder.java` | — | percentage `GridPane` constraints |
| `src/ui/helper/Placeholder*Field.java` | — | `promptText` |
| `src/ui/helper/FasterScrollPane.java` | — | plain `ScrollPane` |
| `src/ui/helper/CustomProgressBarUI.java` | — | `.progress-bar` CSS |
| scattered `new ImageIcon(...)` | `ui/Images.java` | cached, scaled by view |
| duplicated lesson sorting | `ui/Lessons.java` | one sorted list |

Every screen is an FXML + controller pair under `ui/screens/`, laid out like the
old `src/ui/screens/`: `StartPage`, `auth/`, `learning/`, `test/`, `settings/`,
`games/` and `Search`.

`api/` and `auth/` were never UI code and are unchanged, except that
`AuthManager` now navigates through the `AuthNavigator` interface instead of
calling the UI directly.

## Checking your work

`FxmlSmokeTest` loads every screen in one pass. FXML binds at runtime, so a bad
`fx:id` or a handler name that does not exist compiles fine and only fails when
the screen is opened:

```bash
make test                      # both suites
make test-views                # load all 22 screens
make test-clicks               # click through the app
make preview SCREEN=LearningView   # open one screen on its own
```

## Adding a screen

1. Add `<name>.fxml` under `src/main/resources/de/vocabtrainer/ui/screens/…`
   describing the layout.
2. Add `<name>.java` at the matching path under `src/main/java/…`, extending
   `Screen` and returning the FXML name from `fxmlPath()`. `@FXML` fields bind
   by `fx:id`; `onAction="#method"` binds handlers. Put per-node icon wiring in
   `initialize()`.
3. Navigate to it with `App.switchScreen(new <name>())`, and add it to
   `FxmlSmokeTest.SCREENS`.

The FXML is looked up next to the controller class, which is why the two trees
mirror each other. A screen whose FXML lives beside a shared base class (as
`LessonSelector` does) must also override `fxmlOwner()`, since `fxmlPath()`
resolves relative to the concrete class by default.

### Conventions

- **Colours.** Never set a colour in Java. Attach a style class and define it in
  `common.css` using the `-vt-*` variables, so both themes follow.
- **Hover / click.** Handled by `.app-button:hover` and `:pressed`.
- **Hidden nodes.** Set `managed="false"` alongside `visible="false"` so a
  hidden node reserves no space, and flip both together in the controller.
- **Layout.** `GridPane` with `percentWidth`/`percentHeight` for proportional
  splits; `VBox`/`HBox` with `vgrow`/`hgrow` and `maxWidth="Infinity"` for
  stretch. A `GridPane` row needs its own `RowConstraints vgrow="ALWAYS"` —
  `VBox.vgrow` on the GridPane only grows the pane, not its rows.

## Bugs fixed along the way

Where the old code could not be carried over as written, the fix is noted in the
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

## Removed

`src/ui/`, `src/App.java`, `src/Main.java`, and the `javac`-based
`scripts/run.sh`, `scripts/run.py` and `scripts/Makefile`, which could not build
JavaFX. A `Makefile` in the project root replaces them.

Two files under `src/ui/screens/games/` went with them: `GameM.java` and
`FallingWordsGame.java`. Both declared `package minigames` while living
elsewhere, `GameM` did not compile at all, and nothing referenced either — the
old `run.sh` never even compiled them, because its `src/**/*.java` glob did not
reach that far. The falling-words game was therefore not ported; it is in the
git history if you want to revive it.
