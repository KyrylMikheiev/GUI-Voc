import os
import shutil

def delete():
    print("Deleting old files...")
    if os.path.exists("src/*.class"):
        os.remove("src/*.class")
    if os.path.exists("LatinVocabParser/VocabAPI/*.class"):
        os.remove("LatinVocabParser/VocabAPI/*.class")
    if os.path.exists("LatinVocabParser/VocabAPI/WordTypes/*.class"):
        os.remove("LatinVocabParser/VocabAPI/WordTypes/*.class")
    if os.path.exists("minigames/*.class"):
        os.remove("minigames/*.class")
    if os.path.exists("VocabAPI/"):
        shutil.rmtree("VocabAPI/")

def compile():
    delete()
    print("Compiling Java code...")
    os.system('javac -d . src/*.java LatinVocabParser/VocabAPI/*.java LatinVocabParser/VocabAPI/WordTypes/*.java minigames/*.java')

def run():
    compile()
    print("Starting program...")
    os.system('java src/Main')

if __name__ == "__main__":
    run()