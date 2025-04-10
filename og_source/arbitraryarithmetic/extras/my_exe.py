import os,sys

class MyInfArith:

    def __init__(self,mode):
        self.mode = mode
        self.src_path = "arbitraryarithmetic/"+("AInteger.java" if mode == "int" else "AFloat.java")
        self.test_path = "arbitraryarithmetic/MyInfArith"

    def compile(self):
        os.system(f'javac {self.src_path}')
    
    def run(self,op,x1,x2):
        os.system(f'java {self.test_path} {self.mode} {op} {x1} {x2}')

def main():
    args = sys.argv
    arith = MyInfArith(args[1])
    arith.compile()
    arith.run(args[2],args[3],args[4])

if __name__ == "__main__":
    main()