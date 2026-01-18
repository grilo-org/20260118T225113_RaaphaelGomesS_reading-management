package tech.gomes.reading.management.dto.book.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Instant;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookRequestDTO(long id,
                             String status,
                             int pages,
                             @NotNull
                             @DecimalMin(value = "0.5", message = "A avaliação deve ser no mínimo 0.5")
                             @DecimalMax(value = "5.0", message = "A avaliação deve ser no máximo 5.0")
                             double rating,
                             Instant startedDate,
                             Instant finishedDate,
                             long libraryId) {
}
