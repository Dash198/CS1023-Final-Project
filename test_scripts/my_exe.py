import os,sys

class MyInfArith:

    def __init__(self,mode):
        self.mode = mode
        self.package_path = "/home/devansh/repos/CS1023-Final-Project/target/aarithmetic-1.6.jar"
        self.test_path = "test_scripts/MyInfArith"

    def compile(self):
        os.system(f'javac -cp .:{self.package_path} {self.test_path}.java')
    
    def run(self,op,x1,x2):
        os.system(f'java -cp .:{self.package_path} {self.test_path} {self.mode} {op} {x1} {x2}')

def main():
    args = sys.argv
    arith = MyInfArith(args[1])
    arith.compile()
    arith.run(args[2],args[3],args[4])

if __name__ == "__main__":
    main()