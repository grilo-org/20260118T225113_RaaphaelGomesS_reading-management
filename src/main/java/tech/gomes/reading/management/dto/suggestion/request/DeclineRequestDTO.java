package tech.gomes.reading.management.dto.suggestion.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeclineRequestDTO(long id, String justification) {
}
