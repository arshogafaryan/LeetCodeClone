function submitCode() {
    let code = document.getElementById("code").value;
    let languageType = document.getElementById("language").value;
    fetch('http://localhost:8080/save-code', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            code: code,
            languageType: languageType
        })
    })
    .then(response => response.json())
    .then(result => {
        if (result.hasOwnProperty("errorMessage")) {
            document.getElementById("output").value = "❌ Error: " + result.errorMessage;
        } else if (result.hasOwnProperty("failedCase")) {
            let output = result.output;
            let expected = result.expected;
            let failedCase = result.failedCase;
            let outputMessage = `❌ Code failed for case ${failedCase}\n\nOutput:\n${output}\n\nExpected:\n${expected}`;
            document.getElementById("output").value = outputMessage;
            document.getElementById("outputContainer").innerHTML = `Output: ${output}<br>Expected: ${expected}`;
        } else if (result.hasOwnProperty("message")) {
            document.getElementById("output").value = "✅ " + result.message;
            document.getElementById("outputContainer").innerHTML = "";
        } else {
            document.getElementById("output").value = "❌ Unknown error occurred.";
        }
    })
    .catch(error => {
        console.log(error);
    });
  }
  
  function runCode() {
    let code = document.getElementById("code").value;
    let languageType = document.getElementById("language").value;
    fetch('http://localhost:8080/run-code', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            code: code,
            languageType: languageType
        })
    })
    .then(response => response.json())
    .then(result => {
        if (result.hasOwnProperty("errorMessage")) {
            document.getElementById("output").value = "❌ Error: " + result.errorMessage;
        } else if (result.hasOwnProperty("failedCase")) {
            let output = result.output;
            let expected = result.expected;
            let failedCase = result.failedCase;
            let outputMessage = `❌ Code failed for case ${failedCase}\n\nOutput:\n${output}\n\nExpected:\n${expected}`;
            document.getElementById("output").value = outputMessage;
            document.getElementById("outputContainer").innerHTML = `Output: ${output}<br>Expected: ${expected}`;
        } else if (result.hasOwnProperty("message")) {
            document.getElementById("output").value = "✅ " + result.message;
            document.getElementById("outputContainer").innerHTML = "";
        } else {
            document.getElementById("output").value = "❌ Unknown error occurred.";
        }
    })
    .catch(error => {
        console.log(error);
    });
  }
  
  function setDefaultCode() {
      let language = document.getElementById("language").value;
      let code = "";
      switch (language) {
          case "c++":
          code = "class Solution {\npublic:\n  int sumOfTwoNumbers(int firstNum, int secondNum) {\n    \n  }\n};";
          break;
          case "js":
          code = "var sumOfTwoNumbers = function(firstNum, secondNum) {\n    \n};";
          break;
          case "java":
          code = "class Solution {\n    public int sumOfTwoNumbers(int firstNum, int secondNum) {\n        \n    }\n}";
          break;
          case "python":
          code = "class Solution:\n    def sumOfTwoNumbers(self, firstNum:int, secondNum:int) -> int:\n        \n";
          break;
          default:
          alert("Invalid language selected");
      }
      document.getElementById("code").value = code;
  }
  setDefaultCode();
  