package org.example.swp391.controller;

import jakarta.validation.Valid;
import org.example.swp391.dto.request.SliderRequestDTO;
import org.example.swp391.dto.response.SliderResponseDTO;
import org.example.swp391.entity.Slider;
import org.example.swp391.mapper.SliderMapper;
import org.example.swp391.service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sliders")
public class SliderController {

    @Autowired
    private SliderService sliderService;
    
    @Autowired
    private SliderMapper sliderMapper;

    @PostMapping
    public ResponseEntity<SliderResponseDTO> createSlider(@Valid @RequestBody SliderRequestDTO sliderRequestDTO) {
        Slider slider = sliderService.createSlider(sliderRequestDTO);
        return new ResponseEntity<>(sliderMapper.toSliderResponseDTO(slider), HttpStatus.CREATED);
    }

    @PutMapping("/{sliderId}")
    public ResponseEntity<SliderResponseDTO> updateSlider(
            @PathVariable Integer sliderId,
            @Valid @RequestBody SliderRequestDTO sliderRequestDTO) {
        Slider slider = sliderService.updateSlider(sliderId, sliderRequestDTO);
        return ResponseEntity.ok(sliderMapper.toSliderResponseDTO(slider));
    }

    @DeleteMapping("/{sliderId}")
    public ResponseEntity<Void> deleteSlider(@PathVariable Integer sliderId) {
        sliderService.deleteSlider(sliderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sliderId}")
    public ResponseEntity<SliderResponseDTO> getSliderById(@PathVariable Integer sliderId) {
        SliderResponseDTO slider = sliderService.findById(sliderId);
        return ResponseEntity.ok(slider);
    }

    @GetMapping
    public ResponseEntity<List<SliderResponseDTO>> getAllSliders() {
        List<SliderResponseDTO> sliders = sliderService.findAll().stream()
                .map(sliderMapper::toSliderResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sliders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SliderResponseDTO>> getSlidersByStatus(@PathVariable Boolean status) {
        List<SliderResponseDTO> sliders = sliderService.findByStatus(status).stream()
                .map(sliderMapper::toSliderResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sliders);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SliderResponseDTO>> searchSlidersByCaption(@RequestParam String caption) {
        List<SliderResponseDTO> sliders = sliderService.findByCaption(caption).stream()
                .map(sliderMapper::toSliderResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sliders);
    }
} 