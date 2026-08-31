# VocabTrainer

VocabTrainer von Adrian, Jonah, Kyryl

## Ausführen

```bash
./mvnw javafx:run                  # hell
./mvnw javafx:run -Dvt.theme=dark  # dunkel
./scripts/run-fx.sh [dark]         # dasselbe, aktualisiert vorher die Submodule
```

Maven und das JavaFX SDK lädt der Maven Wrapper (`mvnw`) selbst herunter; nur ein
JDK 21 muss installiert sein. JavaFX ist seit Java 11 nicht mehr Teil des JDK,
deshalb lässt sich das Projekt nicht mehr mit `javac` allein bauen.

Die Vokabeln kommen aus dem Submodul `LatinVocabParser`:

```bash
git submodule update --init --recursive
```

## Aufbau

- `src/fx/` — die Oberfläche: pro Screen eine `.fxml` und ein Controller,
  dazu `ui/` mit Fensterverwaltung, Theming und den Stylesheets
- `src/api/` — REST-Client
- `src/auth/` — Login, Token, Benutzer
- `LatinVocabParser/` — Vokabelparser (Submodul)
- `resources/images/` — Icons

Die Oberfläche war ursprünglich in Swing geschrieben; `MIGRATION.md` beschreibt,
was wohin gewandert ist, und wie man einen neuen Screen anlegt.

## Links

[Kanban board](https://cryptpad.fr/kanban/#/2/kanban/edit/HfTW0JeJGJI0bK9wPWr8tnY4/)

[actual kanban lmao](https://github.com/users/Jonah987654321/projects/6)

[icons for UI (must be attributed in credits)](https://www.flaticon.com/search?word=learning&color=black)
