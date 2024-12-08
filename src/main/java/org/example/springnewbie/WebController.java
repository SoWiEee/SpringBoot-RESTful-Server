package org.example.springnewbie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/api-reference")
    public String apiReference() {
        return "api-reference";
    }
}