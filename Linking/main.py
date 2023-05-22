import sys
from Solution import Solution

if __name__ == "__main__":
    num1 = int(sys.argv[1])
    num2 = int(sys.argv[2])
    
    solution = Solution()
    result = solution.sumOfTwoNumbers(num1, num2)
    
    print(result)
