all: compile

del:
	rm -f src/*.class

compile: del
	javac src/Main.java

run: compile
	java src/Main
