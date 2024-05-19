package com.gaenari.backend.domain.member.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Circuit Breaker Test Controller", description = "Circuit Breaker Test Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/circuit")
public class CircuitBreakerTestController {
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @GetMapping("/close")
    public ResponseEntity<Void> close(@RequestParam String name) {
        circuitBreakerRegistry.circuitBreaker(name)
                .transitionToClosedState();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/open")
    public ResponseEntity<Void> open(@RequestParam String name) {
        circuitBreakerRegistry.circuitBreaker(name)
                .transitionToOpenState();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<CircuitBreaker.State> status(@RequestParam String name) {
        CircuitBreaker.State state = circuitBreakerRegistry.circuitBreaker(name)
                .getState();
        return ResponseEntity.ok(state);
    }
}
