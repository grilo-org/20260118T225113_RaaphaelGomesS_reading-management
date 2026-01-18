package tech.gomes.reading.management.dto.book.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PagesUpdateRequestDTO(@NotNull
                                    long bookId,
                                    @NotNull
                                    @Min(0)
                                    int pages) {
}
