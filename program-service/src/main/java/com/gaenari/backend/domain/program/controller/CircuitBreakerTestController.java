package com.gaenari.backend.domain.program.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Circuit Breaker Test Controller", description = "Circuit Breaker Test Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/circuit")
public class CircuitBreakerTestController {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @GetMapping("/close")
    public ResponseEntity<Void> close(@RequestParam String name) {
        circuitBreakerRegistry.circuitBreaker(name).transitionToClosedState();
        log.info("서킷 브레이커 닫힘: {}", name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/open")
    public ResponseEntity<Void> open(@RequestParam String name) {
        circuitBreakerRegistry.circuitBreaker(name).transitionToOpenState();
        log.info("서킷 브레이커 열림: {}", name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<CircuitBreaker.State> status(@RequestParam String name) {
        CircuitBreaker.State state = circuitBreakerRegistry.circuitBreaker(name).getState();
        log.info("서킷 브레이커 상태 확인 - name: {}, state: {}", name, state);
        return ResponseEntity.ok(state);
    }
}
