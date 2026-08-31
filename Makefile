# VocabTrainer
#
# Everything goes through the Maven wrapper, which downloads Maven and the
# JavaFX SDK on first use. Only a JDK 21 has to be installed.
#
#   make            list the targets
#   make run        start the app
#   make test       load every screen and click through the app

# On Windows make runs recipes through cmd.exe, which cannot execute "./mvnw":
# it needs the .cmd wrapper, and a leading ".\" to look in this directory.
ifeq ($(OS),Windows_NT)
  MVN := .\mvnw.cmd -B
else
  MVN := ./mvnw -B
endif

MAIN_PKG := de.vocabtrainer

.DEFAULT_GOAL := help
.PHONY: help run run-dark build clean test test-views test-clicks preview submodules

help: ## List the available targets
	@grep -hE '^[a-zA-Z_-]+:.*?## ' $(MAKEFILE_LIST) \
		| awk -F':.*?## ' '{printf "  make %-14s %s\n", $$1, $$2}'

run: ## Start the app
	@$(MVN) javafx:run

run-dark: ## Start the app in dark mode
	@$(MVN) javafx:run -Dvt.theme=dark

build: ## Compile without running
	@$(MVN) compile

clean: ## Delete build output
	@$(MVN) clean

test: test-views test-clicks ## Run both smoke tests

test-views: ## Load every screen once and report failures
	@$(MVN) javafx:run -DmainClass=$(MAIN_PKG).ui.FxmlSmokeTest

test-clicks: ## Click through the app and report failures
	@$(MVN) javafx:run -DmainClass=$(MAIN_PKG).ui.NavigationSmokeTest

# e.g. make preview SCREEN=LearningView
preview: ## Open one screen on its own (SCREEN=StartPage)
	@$(MVN) javafx:run -DmainClass=$(MAIN_PKG).ui.Preview -Dscreen=$(or $(SCREEN),StartPage)

submodules: ## Fetch/update the vocabulary parser submodule
	@git submodule update --init --recursive
