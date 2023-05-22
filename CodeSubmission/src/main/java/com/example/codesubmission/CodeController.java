package com.example.codesubmission;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@RestController
public class CodeController {
    private String fileName = ""; // Make the fileName variable static so it can be accessed from the static getFileName() method
    public static void main(String[] args) {
        SpringApplication.run(CodeController.class, args);
    }

    @PostMapping("/save-code")
    @CrossOrigin(origins = "*")
    public JsonNode saveCode(@RequestBody String codeRequestJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(codeRequestJson);

            String code = rootNode.get("code").asText();
            String languageType = rootNode.get("languageType").asText();

            String fileName;
            switch (languageType) {
                case "python":
                    fileName = "../Linking/Solution.py";
                    break;
                case "c++":
                    fileName = "../Linking/Solution.cpp";
                    break;
                case "java":
                    fileName = "../Linking/Solution.java";
                    break;
                case "js":
                    fileName = "../Linking/Solution.js";
                    break;
                default:
                    fileName = "../Linking/Solution.txt";
            }

            FileWriter myWriter = new FileWriter(fileName, false);
            myWriter.write(code);
            myWriter.close();

            try {
                ProcessBuilder pb = new ProcessBuilder("python3", "../Compilation/main.py", fileName);
                pb.inheritIO();
                Process p = pb.start();
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            JsonNode errorNode = readErrorJsonFile();
            if (errorNode != null && errorNode.has("Status")) {
                String status = errorNode.get("Status").asText();
                if (status.equals("Compile error")) {
                    return objectMapper.createObjectNode().put("errorMessage", "Compilation error occurred.");
                }
            }

            JsonNode result = readResultJsonFile();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/run-code")
    @CrossOrigin(origins = "*")
    public JsonNode runCode(@RequestBody String codeRequestJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(codeRequestJson);

            String code = rootNode.get("code").asText();
            String languageType = rootNode.get("languageType").asText();

            String fileName;
            switch (languageType) {
                case "python":
                    fileName = "../Linking/Solution.py";
                    break;
                case "c++":
                    fileName = "../Linking/Solution.cpp";
                    break;
                case "java":
                    fileName = "../Linking/Solution.java";
                    break;
                case "js":
                    fileName = "../Linking/Solution.js";
                    break;
                default:
                    fileName = "../Linking/Solution.txt";
            }

            FileWriter myWriter = new FileWriter(fileName, false);
            myWriter.write(code);
            myWriter.close();

            try {
                ProcessBuilder pb = new ProcessBuilder("python3", "../Compilation/main.py", fileName);
                pb.inheritIO();
                Process p = pb.start();
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            JsonNode errorNode = readErrorJsonFile();
            if (errorNode != null && errorNode.has("Status")) {
                String status = errorNode.get("Status").asText();
                if (status.equals("Compile error")) {
                    return objectMapper.createObjectNode().put("errorMessage", "Compilation error occurred.");
                }
            }

            JsonNode result = readResultJsonFile();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonNode readResultJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File resultFile = new File("../Testing/submit.json");
        JsonNode rootNode = objectMapper.readTree(resultFile);

        if (rootNode.has("status") && rootNode.get("status").asText().equals("Passed")) {
            return objectMapper.createObjectNode().put("message", "Code passed!");
        } else if (rootNode.has("case") && rootNode.has("expected") && rootNode.has("output")) {
            String failedCase = rootNode.get("case").asText();
            String output = rootNode.get("output").asText();
            String expected = rootNode.get("expected").asText();

            return objectMapper.createObjectNode()
                    .put("failedCase", failedCase)
                    .put("output", output)
                    .put("expected", expected);
        } else {
            return objectMapper.createObjectNode().put("message", "Unknown error occurred.");
        }
    }

    public JsonNode readErrorJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File errorFile = new File("../Testing/error.json");
        if (errorFile.exists()) {
            return objectMapper.readTree(errorFile);
        }
        return null;
    }


    public String getFileName() {
        return fileName;
    }

}
