package com.gaenari.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/program-service")
public class Health {
    Environment env;
    @Autowired
    public Health(Environment env) {
        this.env = env;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Program service on PORT %s",
                env.getProperty("local.server.port"));
    }
}
