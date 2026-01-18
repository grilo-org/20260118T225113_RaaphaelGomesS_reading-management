package tech.gomes.reading.management.dto.book.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookCreateRequestDTO (BookRequestDTO book, BookTemplateRequestDTO template) {
}
