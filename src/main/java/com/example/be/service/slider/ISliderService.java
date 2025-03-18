package com.example.be.service.slider;

import com.example.be.model.Slider;
import java.util.List;
import java.util.Optional;

public interface ISliderService {
    List<Slider> getAllSliders();
    Optional<Slider> getSliderById(Integer id);
    Slider createSlider(Slider slider);
    Slider updateSlider(Integer id, Slider slider);
    void deleteSlider(Integer id);
}
