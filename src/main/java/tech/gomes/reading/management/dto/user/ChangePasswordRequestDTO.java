package tech.gomes.reading.management.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChangePasswordRequestDTO(@NotBlank(message = "A senha não pode estar em branco.")
                                       String currentPassword,
                                       @NotBlank(message = "A senha não pode estar em branco.")
                                       @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
                                               message = "A senha deve ter entre 8 e 15 caracteres, contendo pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial.")
                                       String newPassword) {
}