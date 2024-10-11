package com.example.netpolix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class NetPoliXApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetPoliXApplication.class, args);
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "home"; // Return the name of the template without the .html extension
    }
}
