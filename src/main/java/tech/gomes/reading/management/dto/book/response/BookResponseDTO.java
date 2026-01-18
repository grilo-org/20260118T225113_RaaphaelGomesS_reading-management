package tech.gomes.reading.management.dto.book.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record BookResponseDTO(long id,
                              long libraryId,
                              String img,
                              String title,
                              String author,
                              String status,
                              int pages,
                              int totalPages,
                              double rating,
                              Instant startedDate,
                              Instant finishedDate) {
}
