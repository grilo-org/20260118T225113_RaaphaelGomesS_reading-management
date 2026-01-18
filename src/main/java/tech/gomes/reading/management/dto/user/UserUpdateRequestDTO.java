package tech.gomes.reading.management.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserUpdateRequestDTO(@Email(message = "O email deve ser válido.")
                                   String email,
                                   String username) {
}
