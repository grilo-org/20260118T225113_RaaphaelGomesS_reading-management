package tech.gomes.reading.management.dto.book.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.util.Set;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookTemplateRequestDTO(Long templateId,
                                     String isbn,
                                     String title,
                                     String author,
                                     String publisher,
                                     String edition,
                                     String description,
                                     int year,
                                     int pages,
                                     Set<String> categories,
                                     String imgUrl) {
}
