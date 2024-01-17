all: compile

compile:
	rm *.class
	javac Main.java
run: compile
	java Main