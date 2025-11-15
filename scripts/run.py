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
    if os.path.exists("restAPI/*.class"):
        os.remove("restAPI/*.class")

def compile():
    delete()
    print("Compiling Java code...")
    os.system('javac -encoding UTF8 -d bin src/**/*.java')
    #os.system('javac -encoding UTF8 -d . src/*.java LatinVocabParser/VocabAPI/*.java LatinVocabParser/VocabAPI/WordTypes/*.java minigames/*.java')

def run():
    os.system("git submodule update --init --recursive")
    os.system("cd LatinVocabParser && git pull origin main && cd ..")
    compile()
    print("Starting program...")
    os.system('java -cp bin src/Main -Dfile.encoding=UTF-8')

if __name__ == "__main__":
    run()
