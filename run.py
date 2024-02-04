import os
import shutil

def delete():
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
    os.system('javac -d . src/*.java LatinVocabParser/VocabAPI/*.java LatinVocabParser/VocabAPI/WordTypes/*.java minigames/*.java')

def run():
    compile()
    os.system('java src/Main')

if __name__ == "__main__":
    run()