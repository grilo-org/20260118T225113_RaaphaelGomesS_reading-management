package tech.gomes.reading.management.dto.book.response;

import lombok.Builder;

@Builder
public record CategoryFinishCountDTO(String categoryName,
                                     double count) {
}
