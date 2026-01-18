package tech.gomes.reading.management.dto.library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibraryRequestDTO(long id,
                                @NotNull
                                String name,
                                @NotNull
                                String description) {
}
