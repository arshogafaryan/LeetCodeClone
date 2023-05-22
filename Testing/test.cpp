#include <sstream>
#include <string>
#include <cstdlib>
#include <cstdio>
#include <sstream>
#include <vector>
#include <unordered_map>
#include <fstream>
#include <iostream>
#include "testCaseRunner.h"
#include "nlohmann/json.hpp"

using json = nlohmann::json;


int main(int argc, char** argv) {
    std::string language(argv[1]);
    testCaseRunner tester("../Testing/file_test");
    std::cout << language << std::endl;
    tester.readFromFile();
    tester.setLanguage(language);
    tester.run();
    json my_obj;
    if (tester.isPassed())
    {
        my_obj = {
            {"status", "Passed"}
        };
    }
    else
    {
        my_obj = {
            {"status", "Failed"},
            {"case", std::to_string(tester.getFailedCase())},
            {"expected", tester.getExpected()},
            {"output", tester.getOutput()}
        };
    }
    std::ofstream output_file("../Testing/submit.json");
    output_file << my_obj.dump();
    output_file.close();


    return EXIT_SUCCESS;
}
