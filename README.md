# VocabTrainer

GUI und Grundgerüst für den VocabTrainer von Adrian, Kyryl, Nick und Niko.

## ToDo's

### data storage stuff

- [ ] database
  - [ ] add mainMenuWidget (integer) column to users db
  - [ ] add daily login streak logger thing
  - [X] rest api
    - [X] register
    - [X] verify
      - [X] email
    - [X] login
    - [X] delete account
    - [X] updateUserVocabstats
    - [ ] forgotPassword
    - [ ] updatePreferences (modePreference, widget, class)
    - [ ] invalidateAllTokens
  - [X] java connector class
    - [X] register
    - [X] login
      - [ ] save token to file
    - [X] verify
      - [ ] save token to file
    - [X] delete account
    - [ ] updateUserVocabStats
    - [ ] forgotPasswd
    - [ ] updatePreferences
    - [ ] invalidateAllTokens
- [ ] CSV
  - [X] fix broken "#NAME" vocabs
  - [ ] add ID column

### UIs

- [ ] navBar

  - [ ] better logo
  - [ ] back button
- [ ] initialSetup

  - [X] register
  - [X] verify
  - [X] login
  - [ ] forgot password
  - [X] confirm app settings screen
    - [X] modePreference
    - [ ] widget
  - [ ] layout
  - [ ] style
- [ ] textHelper

  - [ ] get lessons of words
  - [ ] get vocabs of words
  - [X] layout
  - [ ] style
- [ ] settings

  - [ ] add button to invalidate all session tokens
  - [ ] remove change name stuff
  - [ ] add change class combobox
  - [ ] add change start screen widget stuff
  - [ ] layout
  - [ ] style
- [ ] mainMenu

  - [ ] widget JPanel
  - [ ] daily login streak thing
  - [ ] greet
  - [ ] button descriptions
- [ ] widgets

  - [ ] make a widget class
- [ ] library

  - [X] lesson selector/spinner
    - [ ] fix ugly stuff
  - [X] JButton for each vocab
    - [ ] north-aligned if less vocabs than screen size
- [X] lessonSelection (for various UIs)

  - [X] make abstract class with all common code
  - [X] abstract function with what ui should launch when a lesson is clicked
- [X] memory minigame

  - [ ] style the UI
  - [ ] make button to start screen work again
  - [ ] score/ranking db
  - [ ] make nice button highlighting that makes it easier to play
  - [ ] add min/max board size
- [ ] leaning/flashcard view

  - [ ] style
  - [ ] keybinds
  - [ ] connect to database
- [ ] vocabSet creator

  - [ ] upload/save functionality
  - [ ] layout
  - [ ] style
- [ ] testView

  - [ ] layout
  - [ ] style
- [ ] searchView

  - [ ] layout
  - [ ] style
- [ ] vocabView

  - [ ] get grammar stuff
  - [ ] layout
  - [ ] style

## Links

[Kanban board](https://cryptpad.fr/kanban/#/2/kanban/edit/HfTW0JeJGJI0bK9wPWr8tnY4/)
[icons for UI (must be attributed in credits)](https://www.flaticon.com/search?word=learning&color=black)
