package br.com.lav.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of(
            "application", "LAV",
            "status", "ONLINE",
            "message", "API funcionando"
        );
    }
}
