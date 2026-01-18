package tech.gomes.reading.management.dto.book.response;

import lombok.Builder;

import java.util.Set;

@Builder
public record BookTemplateResponseDTO(long id,
                                      String isbn,
                                      String title,
                                      String author,
                                      String publisher,
                                      String edition,
                                      String description,
                                      int year,
                                      int pages,
                                      String img,
                                      String status,
                                      Set<String> categories) {
}
