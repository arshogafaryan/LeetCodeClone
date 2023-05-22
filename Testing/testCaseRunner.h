#ifndef TEST_CASE_RUNNER
#define TEST_CASE_RUNNER

#include <string>
#include <vector>
#include <unordered_map>

class testCaseRunner
{
public:
    testCaseRunner(const std::string& test_file);
    void readFromFile();
    void setLanguage(const std::string& language);
    void run();
    void parser(std::string& command);
    bool isPassed();
    int getFailedCase();
    std::string getOutput();
    std::string getExpected();
private:
    std::string m_test_file;
    std::string m_language;
    bool passed;
    int failed_case;
    std::string expected;
    std::string output;
    std::vector<std::vector<std::string>> cases;
};

#endif