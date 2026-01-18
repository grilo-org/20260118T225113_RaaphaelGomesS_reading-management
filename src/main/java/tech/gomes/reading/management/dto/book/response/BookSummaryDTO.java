package tech.gomes.reading.management.dto.book.response;

import lombok.Builder;

@Builder
public record BookSummaryDTO(Long templateId,
                             String title,
                             String author,
                             String img) {
}
