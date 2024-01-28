all: compile

del:
	rm -f src/*.class
	rm -f VocabParsing/*.class

compile: del
	javac -cp ".;.\src\mysql-connector-j-8.3.0.jar" src/Main.java

run: compile exec

exec:
	java -cp ".;.\src\mysql-connector-j-8.3.0.jar" src.Main