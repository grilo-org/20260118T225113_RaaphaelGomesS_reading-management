package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.suggestion.request.SuggestionRequestDTO;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;

public class SuggestionBuilder {

    public static SuggestionTemplate from(SuggestionRequestDTO requestDTO, User user, BookTemplate template, String coverImg) {
        return SuggestionTemplate.builder()
                .suggestedISBN(requestDTO.suggestedISBN() != null ? requestDTO.suggestedISBN() : template.getISBN())
                .suggestedTitle(requestDTO.suggestedTitle() != null ? requestDTO.suggestedTitle() : template.getTitle())
                .suggestedAuthor(requestDTO.suggestedAuthor() != null ? requestDTO.suggestedAuthor() : template.getAuthor())
                .suggestedPublisher(requestDTO.suggestedPublisher() != null ? requestDTO.suggestedPublisher() : template.getPublisher())
                .suggestedEdition(requestDTO.suggestedEdition() != null ? requestDTO.suggestedEdition() : template.getEdition())
                .suggestedDescription(requestDTO.suggestedDescription() != null ? requestDTO.suggestedDescription() : template.getDescription())
                .suggestedYear(requestDTO.suggestedYear() != null ? requestDTO.suggestedYear() : template.getYearPublication())
                .suggestedPages(requestDTO.suggestedPages() != null ? requestDTO.suggestedPages() : template.getPages())
                .suggestedImg(coverImg != null ? coverImg : template.getImg())
                .reason(requestDTO.suggestedReason())
                .status(TemplateStatusIndicator.IN_ANALYZE)
                .suggestedCategories(requestDTO.suggestedCategories())
                .user(user)
                .bookTemplate(template)
                .build();
    }
}
