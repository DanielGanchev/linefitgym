package com.appfitgym;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@SpringBootApplication

public class LineFitGymAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LineFitGymAppApplication.class, args);

        String input = "12345";
        String result ="";
        Integer length = input.length();

         if (length % 2 == 0) {
            int half = length / 2;
            result = String.valueOf(input.charAt(half-1) + input.charAt(half));



         } else {
                int half = length / 2;
                result = String.valueOf(input.charAt(half));
         }



  }



}
