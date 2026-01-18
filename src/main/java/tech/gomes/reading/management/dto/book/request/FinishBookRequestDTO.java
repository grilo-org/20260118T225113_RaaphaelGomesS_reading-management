package tech.gomes.reading.management.dto.book.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FinishBookRequestDTO(@NotNull
                                   long bookId,
                                   @NotNull
                                   @DecimalMin(value = "0.5", message = "A avaliação deve ser no mínimo 0.5")
                                   @DecimalMax(value = "5.0", message = "A avaliação deve ser no máximo 5.0")
                                   double rating) {
}
