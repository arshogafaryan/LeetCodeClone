package com.example.codesubmission;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeSubmissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeSubmissionApplication.class, args);
    }

    public static String getFileName() {
        return new CodeController().getFileName();
    }


}
