import os

def delete():
    if os.path.exists("src/*.class"):
        os.remove("src/*.class")

def compile():
    delete()
    os.system("javac src/Main.java")

def run():
    compile()
    os.system("java src.Main")

if __name__ == "__main__":
    run()