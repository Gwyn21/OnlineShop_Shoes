package com.example.be.controller.slider;

import com.example.be.model.Slider;
import com.example.be.service.slider.ISliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/slider")
@CrossOrigin("*")
public class SliderController {
    @Autowired
    private ISliderService sliderService;

    @GetMapping
    public ResponseEntity<List<Slider>> getAllSliders() {
        List<Slider> sliders = sliderService.getAllSliders();
        return ResponseEntity.ok(sliders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSliderById(@PathVariable Integer id) {
        Optional<Slider> slider = sliderService.getSliderById(id);
        return slider.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Không tìm thấy Slider với ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Object> createSlider(@RequestBody Slider slider) {
        try {
            Slider savedSlider = sliderService.createSlider(slider);
            return ResponseEntity.ok("Tạo mới Slider thành công! ID: " + savedSlider.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi tạo mới Slider: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSlider(@PathVariable Integer id, @RequestBody Slider slider) {
        try {
            Slider updatedSlider = sliderService.updateSlider(id, slider);
            return ResponseEntity.ok("Cập nhật Slider thành công! ID: " + updatedSlider.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi cập nhật Slider: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSlider(@PathVariable Integer id) {
        try {
            sliderService.deleteSlider(id);
            return ResponseEntity.ok("Xóa Slider thành công! ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi xóa Slider: " + e.getMessage());
        }
    }
}
