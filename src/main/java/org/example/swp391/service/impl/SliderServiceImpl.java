package org.example.swp391.service.impl;

import org.example.swp391.dto.request.SliderRequestDTO;
import org.example.swp391.dto.response.SliderResponseDTO;
import org.example.swp391.entity.Slider;
import org.example.swp391.exception.AppException;
import org.example.swp391.exception.ErrorCode;
import org.example.swp391.mapper.SliderMapper;
import org.example.swp391.repository.SliderRepository;
import org.example.swp391.service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SliderServiceImpl implements SliderService {

    @Autowired
    private SliderRepository sliderRepository;
    
    @Autowired
    private SliderMapper sliderMapper;

    @Override
    public Slider createSlider(SliderRequestDTO sliderDTO) {
        // Validate required fields
        if (sliderDTO.getStatus() == null) {
            throw new AppException(ErrorCode.STATUS_NULL_ERROR);
        }

        // Convert DTO to Entity
        Slider slider = sliderMapper.toSlider(sliderDTO);

        return sliderRepository.save(slider);
    }

    @Override
    public Slider updateSlider(Integer sliderId, SliderRequestDTO sliderDTO) {
        // Validate slider ID
        if (sliderId == null) {
            throw new AppException(ErrorCode.SLIDER_ID_NULL_ERROR);
        }
        if (sliderId <= 0) {
            throw new AppException(ErrorCode.SLIDER_ID_POSITIVE_ERROR);
        }
        
        // Find slider by ID
        Slider existingSlider = sliderRepository.findById(sliderId)
                .orElseThrow(() -> new AppException(ErrorCode.SLIDER_NOT_EXIST_ERROR));

        // Update entity using mapper
        sliderMapper.updateSlider(existingSlider, sliderDTO);

        return sliderRepository.save(existingSlider);
    }

    @Override
    public void deleteSlider(Integer sliderId) {
        // Validate slider ID
        if (sliderId == null) {
            throw new AppException(ErrorCode.SLIDER_ID_NULL_ERROR);
        }
        if (sliderId <= 0) {
            throw new AppException(ErrorCode.SLIDER_ID_POSITIVE_ERROR);
        }
        
        // Find slider by ID
        Slider slider = sliderRepository.findById(sliderId)
                .orElseThrow(() -> new AppException(ErrorCode.SLIDER_NOT_EXIST_ERROR));
        
        // Delete slider
        sliderRepository.delete(slider);
    }

    @Override
    public SliderResponseDTO findById(Integer sliderId) {
        // Validate slider ID
        if (sliderId == null) {
            throw new AppException(ErrorCode.SLIDER_ID_NULL_ERROR);
        }
        if (sliderId <= 0) {
            throw new AppException(ErrorCode.SLIDER_ID_POSITIVE_ERROR);
        }
        
        return sliderMapper.toSliderResponseDTO(sliderRepository.findById(sliderId)
                .orElseThrow(() -> new AppException(ErrorCode.SLIDER_NOT_EXIST_ERROR)));
    }

    @Override
    public List<Slider> findAll() {
        return sliderRepository.findAll();
    }

    @Override
    public List<Slider> findByStatus(Boolean status) {
        if (status == null) {
            throw new AppException(ErrorCode.STATUS_NULL_ERROR);
        }
        return sliderRepository.findByStatus(status);
    }
    
    @Override
    public List<Slider> findByCaption(String caption) {
        return sliderRepository.findByCaptionContaining(caption);
    }
} 