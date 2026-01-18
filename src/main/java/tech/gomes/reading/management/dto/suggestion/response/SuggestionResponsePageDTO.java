package tech.gomes.reading.management.dto.suggestion.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SuggestionResponsePageDTO(int page, int pageSize, int totalPages, int totalElements,
                                        List<SuggestionResponseDTO> data) {


}
