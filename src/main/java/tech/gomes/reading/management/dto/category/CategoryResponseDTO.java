package tech.gomes.reading.management.dto.category;

import lombok.Builder;

@Builder
public record CategoryResponseDTO(long id, String name) {
}
