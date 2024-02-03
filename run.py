import os

def delete():
    if os.path.exists("src/*.class"):
        os.remove("src/*.class")
    if os.path.exists("LatinVocabParser/VocabAPI/*.class"):
        os.remove("LatinVocabParser/VocabAPI/*.class")
    if os.path.exists("minigames/*.class"):
        os.remove("minigames/*.class")

def compile():
    delete()
    os.system('javac -d . src/*.java LatinVocabParser/VocabAPI/*.java minigames/*.java')

def run():
    compile()
    os.system('java src/Main')

if __name__ == "__main__":
    run()