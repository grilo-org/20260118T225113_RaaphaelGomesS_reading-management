package tech.gomes.reading.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.builder.UserResponseDTOBuilder;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.user.ChangePasswordRequestDTO;
import tech.gomes.reading.management.dto.user.UserResponseDTO;
import tech.gomes.reading.management.dto.user.UserUpdateRequestDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuthService authService;

    @GetMapping("/")
    public ResponseEntity<UserResponseDTO> findUser(JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        UserResponseDTO responseDTO = UserResponseDTOBuilder.from(user);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/")
    public ResponseEntity<UserResponseDTO> updatedUser(@Valid @RequestBody UserUpdateRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(userService.updateUser(requestDTO, user));
    }

    @PostMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody ChangePasswordRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        userService.updatePassword(requestDTO, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        userService.deleteUser(user, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
