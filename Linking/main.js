const sumOfTwoNumbers = require('./Solution.js');

const num1 = parseInt(process.argv[2]);
const num2 = parseInt(process.argv[3]);

const result = sumOfTwoNumbers(num1, num2);

console.log(result);
