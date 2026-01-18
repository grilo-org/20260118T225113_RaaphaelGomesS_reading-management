package tech.gomes.reading.management.dto.note;

import lombok.Builder;

@Builder
public record NoteSummaryDTO(long id, String title) {
}
