package tech.gomes.reading.management.dto.suggestion.response;

import lombok.Builder;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;

@Builder
public record SuggestionUpdateResponseDTO (SuggestionResponseDTO updated, BookTemplateResponseDTO template) {
}
