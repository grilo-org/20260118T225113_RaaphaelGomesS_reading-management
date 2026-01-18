package tech.gomes.reading.management.dto.note;

import lombok.Builder;

@Builder
public record NoteResponseDTO(Long id,
                              String title,
                              String category,
                              String type,
                              Long bookReference,
                              String createdDate,
                              String updatedDate) {
}
