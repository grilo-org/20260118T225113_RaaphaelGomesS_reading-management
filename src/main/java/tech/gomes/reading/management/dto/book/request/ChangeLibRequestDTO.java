package tech.gomes.reading.management.dto.book.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChangeLibRequestDTO(long bookId, long libraryId) {
}
