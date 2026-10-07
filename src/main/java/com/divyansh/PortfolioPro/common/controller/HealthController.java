package com.divyansh.PortfolioPro.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/health-check")
    public String healthCheck() {
        return "Application is running!";
    }
}
