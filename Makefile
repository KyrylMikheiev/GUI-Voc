all: compile

del:
	rm -f src/*.class
	rm -f VocabParsing/*.class
	rm -f minigames/*.class

compile: del
	javac -d . src/*.java LatinVocabParser/VocabAPI/*.java minigames/*.java

run: compile exec

exec:
	java src/Main