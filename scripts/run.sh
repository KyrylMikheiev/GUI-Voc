#!/bin/bash
set -euo pipefail

echo "Deleting old build files & creating new build dir..."
rm -rf bin
mkdir bin

echo Loading submodules...
git submodule update --init --recursive
cd LatinVocabParser 
git pull origin main
cd ..

echo Compiling VocabParser...
cd LatinVocabParser
javac -encoding UTF8 -d ../bin VocabAPI/*.java VocabAPI/**/*.java
echo Copying voc list
cp VocabAPI/voc_list.csv ../bin/VocabAPI/voc_list.csv
cd ..

echo Compiling main program...
javac -encoding UTF8 -d bin -cp .:bin src/*.java src/**/*.java

echo Starting program...
java -cp bin src.Main
