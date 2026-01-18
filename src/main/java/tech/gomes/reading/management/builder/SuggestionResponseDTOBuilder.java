package tech.gomes.reading.management.builder;

import org.springframework.data.domain.Page;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.BookCategory;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponseDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponsePageDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionUpdateResponseDTO;
import tech.gomes.reading.management.utils.ConvertUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SuggestionResponseDTOBuilder {

    public static SuggestionResponsePageDTO fromPage(Page<SuggestionTemplate> suggestionPage) {
        List<SuggestionResponseDTO> responseDTOList = suggestionPage.getContent().isEmpty() ?
                Collections.emptyList() : suggestionPage.getContent().stream().map(SuggestionResponseDTOBuilder::fromSuggestion).toList();

        return SuggestionResponsePageDTO.builder()
                .page(suggestionPage.getNumber())
                .pageSize(suggestionPage.getSize())
                .totalPages(suggestionPage.getTotalPages())
                .totalElements(suggestionPage.getNumberOfElements())
                .data(responseDTOList)
                .build();
    }

    public static SuggestionUpdateResponseDTO toUpdate(SuggestionTemplate suggestion, BookTemplate template) {
        return SuggestionUpdateResponseDTO.builder()
                .updated(fromSuggestion(suggestion))
                .template(fromTemplate(template))
                .build();
    }

    public static SuggestionResponseDTO fromSuggestion(SuggestionTemplate suggestion) {
        return SuggestionResponseDTO.builder()
                .id(suggestion.getId())
                .isbn(suggestion.getSuggestedISBN())
                .title(suggestion.getSuggestedTitle())
                .author(suggestion.getSuggestedAuthor())
                .publisher(suggestion.getSuggestedPublisher())
                .edition(suggestion.getSuggestedEdition())
                .description(suggestion.getSuggestedDescription())
                .year(suggestion.getSuggestedYear())
                .pages(suggestion.getSuggestedPages())
                .img(ConvertUtils.uriCoverImg(suggestion.getSuggestedImg()))
                .reason(suggestion.getReason() == null ? null : suggestion.getReason())
                .justification(suggestion.getJustification() == null ? null : suggestion.getJustification())
                .status(suggestion.getStatus().getValue())
                .categories(suggestion.getSuggestedCategories())
                .username(suggestion.getUser().getUsername())
                .build();
    }

    private static BookTemplateResponseDTO fromTemplate(BookTemplate template) {
        return BookTemplateResponseDTO.builder()
                .id(template.getId())
                .isbn(template.getISBN())
                .title(template.getTitle())
                .author(template.getAuthor())
                .publisher(template.getPublisher())
                .edition(template.getEdition())
                .description(template.getDescription())
                .year(template.getYearPublication())
                .pages(template.getPages())
                .img(ConvertUtils.uriCoverImg(template.getImg()))
                .status(template.getStatus().getValue())
                .categories(template.getCategories().stream().map(BookCategory::getName).collect(Collectors.toSet()))
                .build();
    }
}
