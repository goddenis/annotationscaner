package com.xrm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class MainController {

    private String message  = "Annotation scanner";

    @GetMapping("/")
    public String getMain(Model model) throws IOException{

        model.addAttribute("message", message);

        return "index";
    }

}
