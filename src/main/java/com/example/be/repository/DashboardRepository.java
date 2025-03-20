package com.example.be.repository;

import com.example.be.model.OderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends JpaRepository<OderProduct, Integer> {

    @Query(value = "SELECT CAST(COALESCE(SUM(CAST(o.total_pay AS UNSIGNED)), 0) AS CHAR) FROM oder_product o", nativeQuery = true)
    String fetchDashboardOverview();

    @Query(value = "SELECT CAST(COALESCE(SUM(CAST(o.total_pay AS UNSIGNED)), 0) AS CHAR) FROM oder_product o WHERE o.payment_method = 'Online'", nativeQuery = true)
    String fetchMarketingCampaignStats();

    @Query(value = "SELECT CAST(COALESCE(SUM(CAST(o.total_pay AS UNSIGNED)), 0) AS CHAR) FROM oder_product o WHERE o.status = 'Completed'", nativeQuery = true)
    String fetchSaleStats();

    @Query(value = "SELECT CAST(COALESCE(SUM(CAST(o.total_pay AS UNSIGNED)), 0) AS CHAR) FROM oder_product o WHERE o.status = 'Processing'", nativeQuery = true)
    String fetchMarketingStats();
}
