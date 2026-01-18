package tech.gomes.reading.management.dto.book.response;

import lombok.Builder;

@Builder
public record FullBookResponseDTO(BookResponseDTO book, BookTemplateResponseDTO template) {
}
