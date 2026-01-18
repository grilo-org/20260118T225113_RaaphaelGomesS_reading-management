package tech.gomes.reading.management.dto.suggestion.response;

import lombok.Builder;

import java.util.Set;

@Builder
public record SuggestionResponseDTO(long id,
                                    String isbn,
                                    String title,
                                    String author,
                                    String publisher,
                                    String edition,
                                    String description,
                                    int year,
                                    int pages,
                                    String img,
                                    String reason,
                                    String justification,
                                    String status,
                                    Set<String> categories,
                                    String username) {
}
