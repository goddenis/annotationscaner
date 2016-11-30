package com.xrm;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class Main {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class, args);


    }

}
