package org.example.swp391.repository;

import org.example.swp391.entity.Slider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SliderRepository extends JpaRepository<Slider, Integer> {

    // Tìm kiếm slider theo status
    List<Slider> findByStatus(Boolean status);
    
    // Tìm kiếm slider theo status với phân trang
    Page<Slider> findByStatus(Boolean status, Pageable pageable);
    
    // Tìm kiếm slider theo caption
    List<Slider> findByCaptionContaining(String caption);
    
    // Lấy danh sách slider tạo gần đây nhất
    List<Slider> findTop5ByOrderByCreatedAtDesc();
    
    // Lấy danh sách slider theo status và sắp xếp theo thời gian tạo giảm dần
    List<Slider> findByStatusOrderByCreatedAtDesc(Boolean status);
} 