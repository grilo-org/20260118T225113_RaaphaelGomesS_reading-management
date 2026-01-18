package tech.gomes.reading.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.dto.auth.LoginRequestDTO;
import tech.gomes.reading.management.dto.auth.LoginResponseDTO;
import tech.gomes.reading.management.dto.user.UserRequestDTO;
import tech.gomes.reading.management.dto.user.UserResponseDTO;
import tech.gomes.reading.management.exception.UserException;
import tech.gomes.reading.management.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) throws UserException {
        return ResponseEntity.ok(authService.authenticateUser(requestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO requestDTO) throws UserException {
        return ResponseEntity.ok(authService.registerUser(requestDTO));
    }
}
