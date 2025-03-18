package com.example.be.service.slider.impl;

import com.example.be.model.Slider;
import com.example.be.repository.SliderRepository;

import com.example.be.service.slider.ISliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SliderServiceImpl implements ISliderService {
    @Autowired
    private SliderRepository sliderRepository;

    @Override
    public List<Slider> getAllSliders() {
        return sliderRepository.findAll();
    }

    @Override
    public Optional<Slider> getSliderById(Integer id) {
        return sliderRepository.findById(id);
    }

    @Override
    public Slider createSlider(Slider slider) {
        return sliderRepository.save(slider);
    }

    @Override
    public Slider updateSlider(Integer id, Slider slider) {
        if (sliderRepository.existsById(id)) {
            slider.setId(id);
            return sliderRepository.save(slider);
        }
        return null;
    }

    @Override
    public void deleteSlider(Integer id) {
        sliderRepository.deleteById(id);
    }
}
