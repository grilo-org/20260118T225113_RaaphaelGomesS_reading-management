package tech.gomes.reading.management.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequestDTO(@NotBlank(message = "O identificador não deve estar em branco: Insira o email ou username.")
                              String identifier,
                              @NotBlank(message = "A senha não deve estar em branco.")
                              String password) {
}
