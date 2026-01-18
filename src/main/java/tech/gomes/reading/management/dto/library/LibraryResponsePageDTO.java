package tech.gomes.reading.management.dto.library;

import lombok.Builder;

import java.util.List;

@Builder
public record LibraryResponsePageDTO(int page, int pageSize, int totalPages, int totalElements,
                                     List<LibraryResponseDTO> libraries) {
}
