package tech.gomes.reading.management.dto.suggestion.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.util.Set;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record SuggestionRequestDTO(Long templateId,
                                   String suggestedISBN,
                                   String suggestedTitle,
                                   String suggestedAuthor,
                                   String suggestedPublisher,
                                   String suggestedEdition,
                                   String suggestedReason,
                                   String suggestedDescription,
                                   Integer suggestedYear,
                                   Integer suggestedPages,
                                   Set<String> suggestedCategories) {
}
