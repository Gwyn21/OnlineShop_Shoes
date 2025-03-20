package com.example.be.service.dashboard.impl;

import com.example.be.repository.DashboardRepository;
import com.example.be.service.dashboard.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private DashboardRepository dashboardRepository;

    @Override
    public String getDashboardOverview() {
        return dashboardRepository.fetchDashboardOverview();
    }

    @Override
    public String getMarketingCampaignStats() {
        return dashboardRepository.fetchMarketingCampaignStats();
    }

    @Override
    public String getSaleStats() {
        return dashboardRepository.fetchSaleStats();
    }

    @Override
    public String getMarketingStats() {
        return dashboardRepository.fetchMarketingStats();
    }
}
