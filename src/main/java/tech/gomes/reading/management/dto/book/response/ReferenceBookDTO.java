package tech.gomes.reading.management.dto.book.response;

import lombok.Builder;

@Builder
public record ReferenceBookDTO(Long id,
                               String title) {
}
