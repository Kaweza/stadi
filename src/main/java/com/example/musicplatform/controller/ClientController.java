package com.example.musicplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    @GetMapping("/")
    public String home() {
        return "home"; // maps to home.html in templates
    }

    @GetMapping("/audio")
    public String audio() {
        return "audio"; // maps to audio.html
    }

    @GetMapping("/video")
    public String video() {
        return "video"; // maps to video.html
    }
}
