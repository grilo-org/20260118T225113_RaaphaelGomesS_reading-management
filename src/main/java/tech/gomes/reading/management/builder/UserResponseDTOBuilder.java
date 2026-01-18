package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.user.UserResponseDTO;

public class UserResponseDTOBuilder {

    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(user.getId(), user.getEmail(), user.getUsername());
    }
}
