package tech.gomes.reading.management.dto.book.response;

import lombok.Builder;

@Builder
public record BookStatusCountDTO(String status,
                                 double count) {
}
