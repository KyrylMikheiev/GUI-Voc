all: compile

del:
	rm -f src/*.class
	rm -f VocabParsing/*.class

compile: del
	javac src/Main.java

run: compile exec

exec:
	java src.Main