package tech.gomes.reading.management.dto.note;

import lombok.Builder;

import java.util.List;

@Builder
public record NoteResponsePageDTO(int page, int pageSize, int totalPages, int totalElements,
                                  List<NoteResponseDTO> data) {
}
