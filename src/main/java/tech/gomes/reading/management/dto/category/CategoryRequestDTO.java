package tech.gomes.reading.management.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CategoryRequestDTO(long id,
                                 @NotBlank
                                 String name) {
}
