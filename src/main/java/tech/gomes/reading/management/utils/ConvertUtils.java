package tech.gomes.reading.management.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.dto.book.request.BookTemplateRequestDTO;

@Component
public class ConvertUtils {

    public static String getIdentifierByRequestDTO(BookTemplateRequestDTO requestDTO) {
        return requestDTO.isbn() != null ? requestDTO.isbn() : (requestDTO.title() + requestDTO.author()).toLowerCase();
    }

    public static String getIdentifierBySuggestion(SuggestionTemplate suggestion) {
        return suggestion.getSuggestedISBN() != null ? suggestion.getSuggestedISBN()
                : (suggestion.getSuggestedTitle() + suggestion.getSuggestedAuthor()).toLowerCase();
    }

    public static String uriCoverImg(String imgNameOrUrl) {
        if (imgNameOrUrl == null || imgNameOrUrl.isBlank()) {
            return null;
        }

        if (imgNameOrUrl.startsWith("http://") || imgNameOrUrl.startsWith("https://")) {
            return imgNameOrUrl;
        }

        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/uploads/")
                .path(imgNameOrUrl)
                .toUriString();
    }

    public static String normalizeCategoryName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        String trimmedName = name.trim();
        return Character.toUpperCase(trimmedName.charAt(0)) + trimmedName.substring(1).toLowerCase();
    }
}
