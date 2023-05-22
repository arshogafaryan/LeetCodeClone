import subprocess
import json

def compile_cpp(file:str):
    filename = file
    outname = filename[:filename.find(".cpp")]
    command = ["g++", "../Linking/main.cpp", "-o", outname]
    process = subprocess.run(command, capture_output=True)
    if process.returncode == 1:
        return ("Compile error", process.stderr.decode("utf-8"))
    return ("Success", outname)

def compile_java(file:str):
    filename = file
    outname = filename.replace(".java", ".class")
    command = ["javac", filename]
    process = subprocess.run(command, capture_output=True)
    if process.returncode == 1:
        return ("Compile error", process.stderr.decode("utf-8"))
    return ("Success", outname)

def compile_js(file:str):
        
    with open(file, "r") as f:
        contents = f.read()

    contents += "\nmodule.exports = sumOfTwoNumbers;"

    with open(file, "w") as f:
        f.write(contents)

    command = ["node", file]
    process = subprocess.run(command, capture_output=True)
    if process.returncode == 1:
        return ("Compile error", process.stderr.decode("utf-8"))
    return ("Success", file)

def compile_python(file:str):
    filename = file
    command = ["python3", filename]
    process = subprocess.run(command, capture_output=True)
    if process.returncode == 1:
        return ("Compile error", process.stderr.decode("utf-8"))
    return ("Success", file)

def compile_code(file:str, json_file:str):
    extension = file[file.rfind("."):]
    langDict = {".cpp": compile_cpp,
                ".py": compile_python,
                ".java": compile_java,
                ".js": compile_js
                }
    
    if extension in langDict:
        res = langDict[extension](file)
    else:
        res = "Unsupported file extension"
    json_res = {"Status": res[0]} 
    with open(json_file, "w") as file:
        json.dump(json_res, file)