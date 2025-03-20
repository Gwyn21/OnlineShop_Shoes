package com.example.be.controller;

import com.example.be.service.dashboard.impl.DashboardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardController {
    @Autowired
    private DashboardServiceImpl dashboardService;

    @GetMapping("/overview")
    public ResponseEntity<String> getDashboardOverview() {
        return ResponseEntity.ok(dashboardService.getDashboardOverview());
    }

    @GetMapping("/marketing-campaign-stats")
    public ResponseEntity<String> getMarketingCampaignStats() {
        return ResponseEntity.ok(dashboardService.getMarketingCampaignStats());
    }

    @GetMapping("/sale-stats")
    public ResponseEntity<String> getSaleStats() {
        return ResponseEntity.ok(dashboardService.getSaleStats());
    }

    @GetMapping("/marketing-stats")
    public ResponseEntity<String> getMarketingStats() {
        return ResponseEntity.ok(dashboardService.getMarketingStats());
    }
}
