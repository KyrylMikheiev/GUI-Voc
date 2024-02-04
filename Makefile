all: compile

del:
	rm -f src/*.class
	rm -f minigames/*.class
	rm -f LatinVocabParser/VocabAPI/*.class
	rm -f LatinVocabParser/VocabAPI/WordTypes/*.class
	rm -rf VocabAPI/

compile: del
	javac -d . src/*.java LatinVocabParser/VocabAPI/*.java LatinVocabParser/VocabAPI/WordTypes/*.java minigames/*.java

run: compile exec

exec:
	java src/Main