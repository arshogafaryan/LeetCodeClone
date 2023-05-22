import sys
import os
import subprocess
from compile import compile_code as Compile

if __name__ == "__main__":
    file = sys.argv[1]

    Compile(file, "../Testing/error.json")

    # Compile the source files
    compile_command = ["g++","-std=c++20", "../Testing/test.cpp", "../Testing/testCaseRunner.cpp", "-o", "../Testing/test"]
    subprocess.run(compile_command, check=True)

    # Execute the compiled program
    languageType = str(os.path.splitext(file)[1])[1:]
    print(languageType)
    execution_command = ["../Testing/test", str(languageType)]  # Assumes the executable is in the same directory
    # print(type(execution_command))
    subprocess.run(execution_command, check=True)

