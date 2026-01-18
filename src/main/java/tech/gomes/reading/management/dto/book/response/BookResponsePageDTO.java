package tech.gomes.reading.management.dto.book.response;

import lombok.Builder;

import java.util.List;

@Builder
public record BookResponsePageDTO(int page, int pageSize, int totalPages, int totalElements,
                                  List<BookResponseDTO> data) {
}
