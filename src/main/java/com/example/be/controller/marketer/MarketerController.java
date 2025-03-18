package com.example.be.controller.marketer;

import com.example.be.model.Marketer;
import com.example.be.service.marketer.IMarketerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marketer")
@CrossOrigin("*")
public class MarketerController {
    @Autowired
    private IMarketerService marketerService;

    @GetMapping
    public ResponseEntity<List<Marketer>> getAllMarketers() {
        List<Marketer> marketers = marketerService.getAllMarketers();
        return ResponseEntity.ok(marketers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMarketerById(@PathVariable Integer id) {
        Optional<Marketer> marketer = marketerService.getMarketerById(id);
        return marketer.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Không tìm thấy Marketer với ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Object> createMarketer(@RequestBody Marketer marketer) {
        try {
            Marketer savedMarketer = marketerService.createMarketer(marketer);
            return ResponseEntity.ok("Tạo mới Marketer thành công! ID: " + savedMarketer.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi tạo mới Marketer: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMarketer(@PathVariable Integer id, @RequestBody Marketer marketer) {
        try {
            Marketer updatedMarketer = marketerService.updateMarketer(id, marketer);
            return ResponseEntity.ok("Cập nhật Marketer thành công! ID: " + updatedMarketer.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi cập nhật Marketer: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMarketer(@PathVariable Integer id) {
        try {
            marketerService.deleteMarketer(id);
            return ResponseEntity.ok("Xóa Marketer thành công! ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi xóa Marketer: " + e.getMessage());
        }
    }
}
