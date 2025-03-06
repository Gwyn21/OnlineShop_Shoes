package org.example.swp391.mapper;

import org.example.swp391.dto.request.SliderRequestDTO;
import org.example.swp391.dto.response.SliderResponseDTO;
import org.example.swp391.entity.Slider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SliderMapper {
    @Mapping(target = "sliderId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Slider toSlider(SliderRequestDTO sliderRequestDTO);

    @Mapping(target = "sliderId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateSlider(@MappingTarget Slider slider, SliderRequestDTO request);

    SliderResponseDTO toSliderResponseDTO(Slider slider);
} 