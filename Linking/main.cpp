#include <iostream>
#include "Solution.cpp"
using namespace std;


int main(int argc, char** argv) {
    int num1 = std::stoi(argv[1]), num2 = std::stoi(argv[2]);
    Solution s;
    int result = s.sumOfTwoNumbers(num1, num2);
    std::cout << result << std::endl;

    return 0;
}
