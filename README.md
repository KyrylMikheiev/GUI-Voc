# VocabTrainer

VocabTrainer von Adrian, Jonah, Kyryl

## Ausführen

```bash
make            # zeigt alle Befehle
make run        # App starten
make run-dark   # App im Dunkelmodus starten
make test       # alle Screens laden und durchklicken
```

Nötig ist nur ein JDK 21. Maven und das JavaFX SDK lädt der Maven Wrapper beim
ersten Aufruf selbst herunter — JavaFX ist seit Java 11 nicht mehr Teil des JDK,
deshalb lässt sich das Projekt nicht mit `javac` allein bauen.

### Submodul

Die Vokabeln kommen aus `LatinVocabParser`, einem Git-Submodul. Ein frisch
geklontes Repo enthält den Ordner leer — ohne ihn schlägt der Build mit
`Package VocabAPI ist nicht vorhanden` fehl:

```bash
git clone <url>
cd GUI-Voc
make submodules     # holt den Parser (einmalig nach dem Klonen)
make run
```

Das Repo speichert nur URL und Commit-Hash des Parsers, nicht dessen Dateien.
`make submodules` checkt genau den Stand aus, gegen den hier gebaut wurde.
Um auf eine neuere Version zu wechseln:

```bash
git submodule update --remote LatinVocabParser
git add LatinVocabParser && git commit    # der neue Hash gehört ins Repo
```

Der Dunkelmodus lässt sich auch in der App unter *Einstellungen → Dunkelmodus*
umschalten; `make run-dark` startet nur direkt darin.

## Aufbau

Standard-Maven-Layout:

```text
src/main/java/de/vocabtrainer/
    Main.java          Einstiegspunkt (JavaFX Application)
    App.java           Navigation zwischen den Screens
    api/               REST-Client
    auth/              Login, Token, Benutzer
    ui/                Fenster, Screen-Basisklasse, Theme, Hilfsklassen
    ui/screens/        ein Controller pro Screen
src/main/resources/de/vocabtrainer/
    ui/                *.fxml und *.css, jeweils neben ihrem Controller
    images/            Icons
```

Jeder Screen besteht aus einer `.fxml` (Layout) und einer gleichnamigen
Java-Klasse (Verhalten). Farben stehen ausschließlich in `ui/common.css` und den
beiden Theme-Dateien.

`LatinVocabParser/` ist ein Submodul, `drafts/` enthält Entwurfsdateien und
`scripts/` ein Hilfsskript für die CSV-Liste — beides gehört nicht zur App.

Die Oberfläche war ursprünglich in Swing geschrieben; `MIGRATION.md` beschreibt,
was wohin gewandert ist, und wie man einen neuen Screen anlegt.

## Links

[Kanban board](https://cryptpad.fr/kanban/#/2/kanban/edit/HfTW0JeJGJI0bK9wPWr8tnY4/)

[actual kanban lmao](https://github.com/users/Jonah987654321/projects/6)

[icons for UI (must be attributed in credits)](https://www.flaticon.com/search?word=learning&color=black)
