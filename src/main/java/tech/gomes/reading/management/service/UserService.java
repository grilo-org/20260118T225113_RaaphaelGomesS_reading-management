package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.UserResponseDTOBuilder;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.user.ChangePasswordRequestDTO;
import tech.gomes.reading.management.dto.user.UserResponseDTO;
import tech.gomes.reading.management.dto.user.UserUpdateRequestDTO;
import tech.gomes.reading.management.exception.UserException;
import tech.gomes.reading.management.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO updateUser(UserUpdateRequestDTO requestDTO, User userUpdated) throws UserException {

        String identifier = requestDTO.email() != null ? requestDTO.email() : requestDTO.username();

        Optional<User> optionalUser = userRepository.findByIdentifier(identifier);

        if (optionalUser.isPresent()) {
            throw new UserException("Usuário já cadastrado com o identificador: " + identifier, HttpStatus.BAD_REQUEST);
        }

        if (requestDTO.email() != null) {
            userUpdated.setEmail(requestDTO.email());
        }

        if (requestDTO.username() != null) {
            userUpdated.setUsername(requestDTO.username());
        }

        User user = userRepository.save(userUpdated);

        return UserResponseDTOBuilder.from(user);
    }

    public void updatePassword(ChangePasswordRequestDTO passwordRequestDTO, User user) throws UserException {

        if (!passwordEncoder.matches(passwordRequestDTO.currentPassword(), user.getPassword())) {
            throw new UserException("A senha está incorreta.", HttpStatus.BAD_REQUEST);
        }

        if (passwordEncoder.matches(passwordRequestDTO.newPassword(), user.getPassword())) {
            throw new UserException("A nova senha é a mesma que a senha atual.", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(passwordRequestDTO.newPassword()));

        userRepository.save(user);
    }

    public void deleteUser(User user, Long userId) throws Exception {

        User userToDelete = userRepository.findById(userId).orElseThrow(() -> new UserException("O usuário não foi encontrado.", HttpStatus.NOT_FOUND));

        if (!userToDelete.getId().equals(user.getId())) {
            throw new UserException("Não possui permissão para deletar esse usuário.", HttpStatus.FORBIDDEN);
        }

        userRepository.delete(user);
    }
}
