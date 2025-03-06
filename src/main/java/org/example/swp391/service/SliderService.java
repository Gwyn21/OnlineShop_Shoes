package org.example.swp391.service;

import org.example.swp391.dto.request.SliderRequestDTO;
import org.example.swp391.dto.response.SliderResponseDTO;
import org.example.swp391.entity.Slider;

import java.util.List;

public interface SliderService {
    Slider createSlider(SliderRequestDTO sliderDTO);

    Slider updateSlider(Integer sliderId, SliderRequestDTO sliderDTO);

    void deleteSlider(Integer sliderId);

    SliderResponseDTO findById(Integer sliderId);

    List<Slider> findAll();

    List<Slider> findByStatus(Boolean status);
    
    List<Slider> findByCaption(String caption);
} 