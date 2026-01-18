package tech.gomes.reading.management.dto;

import lombok.Builder;
import tech.gomes.reading.management.dto.book.response.BookStatusCountDTO;
import tech.gomes.reading.management.dto.book.response.CategoryFinishCountDTO;

import java.util.List;

@Builder
public record StatisticsResponseDTO(Long averagePagesReadInDay,
                                    Long averageReadingTimeInDays,
                                    List<BookStatusCountDTO> statusCounts,
                                    List<CategoryFinishCountDTO> finishedBooksByCategory
) {
}
