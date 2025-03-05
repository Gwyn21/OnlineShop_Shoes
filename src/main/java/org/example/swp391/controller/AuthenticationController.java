package org.example.swp391.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.swp391.dto.request.AuthenticationRequestDTO;
import org.example.swp391.dto.response.ApiResponse;
import org.example.swp391.dto.response.AuthenticationResponseDTO;
import org.example.swp391.service.impl.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        boolean result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponseDTO>builder()
                .result(AuthenticationResponseDTO.builder()
                        .authenticated(result)
                        .build())
                .build();

    }

}
