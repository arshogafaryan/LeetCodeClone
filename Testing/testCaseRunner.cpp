#include "testCaseRunner.h"    
#include <fstream>
#include <iostream>
#include <sstream>
#include <vector>

testCaseRunner::testCaseRunner(const std::string& test_file)
    : m_test_file{test_file}
{}

void testCaseRunner::readFromFile()
{
    std::ifstream in_file(m_test_file);
    std::string line;
    while (getline(in_file, line))
    {
        std::stringstream ss(line);
        std::vector<std::string> words;
        std::string word;

        while (ss >> word) {
            words.push_back(word);
        }
        cases.push_back(words);
    }
    in_file.close();
}

void testCaseRunner::setLanguage(const std::string& language)
{
    m_language = language;
}

void testCaseRunner::parser(std::string& command)
{
    std::string tmp = command;
    for (int i = 0; i < cases.size(); ++i) 
    {
        std::string command_output = "";
        command = tmp;
        command += ( " " + cases[i][0] + " " + cases[i][1]);
        FILE* pipe2 = popen(command.c_str(), "r");
        if (!pipe2) {
            std::cerr << "Error: failed to open pipe to command." << std::endl;
            return ;
        }

        std::stringstream ss2;
        char buffer2[128];
        while (fgets(buffer2, sizeof(buffer2), pipe2) != NULL) {
            ss2 << buffer2;
        }
        command_output = ss2.str();
        command_output.pop_back();
        // std::cout << command_output << std::endl;
        // std::cout << cases[i][0] << "+" << cases[i][1] << "=" << cases[i][2] <<std::endl;
        if (command_output == cases[i][2])
            std::cout << "Test " << i + 1 << " passed!!!" << std::endl;
        else
        {
            std::cout << "Test " << i + 1 << " failed!!!" << std::endl;
            failed_case = i + 1;
            std::cout << "Output -> " << "[" << command_output << "]" << std::endl;
            output = "[" + command_output + "]";
            std::cout << "Expected -> " << "[" << cases[i][2] << "]" << std::endl;
            expected = "[" + cases[i][2] + "]";
            passed = false;
            pclose(pipe2);
            return;
        }
        passed = true;
        pclose(pipe2);
    }

}


void testCaseRunner::run()
{
    std::string command;
    if (m_language == "cpp")
    {
        command = "../Linking/Solution";
    }
    else if (m_language == "py")
    {
        command = "python3 ../Linking/main.py";
    }
    else if (m_language == "java")
    {
        command = "java -cp .:../Linking Main";
    }
    else if (m_language == "js")
    {
        command = "node ../Linking/main.js";
    }
    else
    {
        return;
    }
    parser(command);

}

bool testCaseRunner::isPassed()
{
    return passed;
}

int testCaseRunner::getFailedCase()
{
    return failed_case;
}

std::string testCaseRunner::getExpected()
{
    return expected;
}

std::string testCaseRunner::getOutput()
{
    return output;
}