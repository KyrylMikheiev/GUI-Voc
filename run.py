import os

def delete():
    if os.path.exists("src/*.class"):
        os.remove("src/*.class")
    if os.path.exists("VocabParsing/*.class"):
        os.remove("VocabParsing/*.class")

def compile():
    delete()
    os.system('javac -cp ".;.\src\mysql-connector-j-8.3.0.jar" src/Main.java')

def run():
    compile()
    os.system('java -cp ".;.\src\mysql-connector-j-8.3.0.jar" src.Main')

if __name__ == "__main__":
    run()