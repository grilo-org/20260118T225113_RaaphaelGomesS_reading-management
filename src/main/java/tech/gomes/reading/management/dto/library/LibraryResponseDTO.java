package tech.gomes.reading.management.dto.library;

import lombok.Builder;

@Builder
public record LibraryResponseDTO(Long id, String name, String description) {
}
