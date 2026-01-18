package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.UserBuilder;
import tech.gomes.reading.management.builder.UserResponseDTOBuilder;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.auth.LoginRequestDTO;
import tech.gomes.reading.management.dto.auth.LoginResponseDTO;
import tech.gomes.reading.management.dto.user.UserRequestDTO;
import tech.gomes.reading.management.dto.user.UserResponseDTO;
import tech.gomes.reading.management.exception.UserException;
import tech.gomes.reading.management.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtEncoder jwtEncoder;

    private final UserBuilder userBuilder;

    public LoginResponseDTO authenticateUser(LoginRequestDTO requestDTO) throws UserException {

        if (requestDTO.identifier() == null || requestDTO.identifier().isBlank()) {
            throw new UserException("O identificador não pode estar em branco", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByIdentifier(requestDTO.identifier())
                .orElseThrow(() -> new UserException("Não foi possível encontrar o usuário pelo identificador: " + requestDTO.identifier(), HttpStatus.NOT_FOUND));


        if (!passwordEncoder.matches(requestDTO.password(), user.getPassword())) {
            throw new UserException("A senha está incorreta.", HttpStatus.BAD_REQUEST);
        }

        var claims = JwtClaimsSet.builder()
                .issuer("reading.management")
                .subject(user.getId().toString())
                .expiresAt(getExpirationDate())
                .issuedAt(Instant.now())
                .claim("scope", user.getRole().name())
                .build();

        var token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseDTO(token, getExpirationDate());
    }

    public UserResponseDTO registerUser(UserRequestDTO requestDTO) throws UserException {

        String identifier = requestDTO.email() != null ? requestDTO.email() : requestDTO.username();

        Optional<User> optionalUser = userRepository.findByIdentifier(identifier);

        if (optionalUser.isPresent()) {
            throw new UserException("Usuário já cadastrado com o identificador: " + identifier, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.save(userBuilder.from(requestDTO));

        return UserResponseDTOBuilder.from(user);
    }

    public User getUserByToken(JwtAuthenticationToken token) throws Exception {
        return userRepository.findById(Long.valueOf(token.getName()))
                .orElseThrow(() -> new UserException("O usuário não foi encontrado.", HttpStatus.NOT_FOUND));
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2L).toInstant(ZoneOffset.of("-03:00"));
    }
}
