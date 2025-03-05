package org.example.swp391.mapper;

import org.example.swp391.dto.request.BlogRequestDTO;
import org.example.swp391.dto.response.BlogResponseDTO;
import org.example.swp391.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    Blog toBlog(BlogRequestDTO blogRequestDTO);

    void updateBlog(@MappingTarget Blog blog, BlogRequestDTO request);

    BlogResponseDTO toBlogResponseDTO(Blog blog);
} 