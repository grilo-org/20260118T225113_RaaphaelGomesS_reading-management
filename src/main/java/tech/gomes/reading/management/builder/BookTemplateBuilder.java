package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.BookCategory;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.dto.book.request.BookTemplateRequestDTO;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;

import java.util.Set;

public class BookTemplateBuilder {

    public static BookTemplate from(SuggestionTemplate suggestion, Set<BookCategory> categories) {
        return BookTemplate.builder()
                .id(suggestion.getBookTemplate() != null ? suggestion.getBookTemplate().getId() : null)
                .ISBN(suggestion.getSuggestedISBN())
                .title(suggestion.getSuggestedTitle())
                .author(suggestion.getSuggestedAuthor())
                .publisher(suggestion.getSuggestedPublisher())
                .edition(suggestion.getSuggestedEdition())
                .description(suggestion.getSuggestedDescription())
                .yearPublication(suggestion.getSuggestedYear())
                .pages(suggestion.getSuggestedPages())
                .img(suggestion.getSuggestedImg())
                .categories(categories)
                .build();
    }

    public static BookTemplate from(BookTemplateRequestDTO requestDTO, Set<BookCategory> categories, String coverImg) {
        return BookTemplate.builder()
                .id(requestDTO.templateId())
                .ISBN(requestDTO.isbn())
                .title(requestDTO.title())
                .author(requestDTO.author())
                .titleAuthor(requestDTO.isbn() == null ? (requestDTO.title() + requestDTO.author()).toLowerCase() : null)
                .publisher(requestDTO.publisher())
                .edition(requestDTO.edition())
                .description(requestDTO.description())
                .yearPublication(requestDTO.year())
                .pages(requestDTO.pages())
                .status(TemplateStatusIndicator.IN_ANALYZE)
                .img(coverImg)
                .categories(categories)
                .build();
    }

    public static void updateBookTemplate(BookTemplate bookTemplate, BookTemplateRequestDTO requestDTO, Set<BookCategory> categories, String coverImg) {
        bookTemplate.setISBN(requestDTO.isbn());
        bookTemplate.setTitle(requestDTO.title());
        bookTemplate.setAuthor(requestDTO.author());
        bookTemplate.setPublisher(requestDTO.publisher());
        bookTemplate.setEdition(requestDTO.edition());
        bookTemplate.setDescription(requestDTO.description());
        bookTemplate.setPages(requestDTO.pages());
        bookTemplate.setImg(coverImg != null ? coverImg : bookTemplate.getImg());
        bookTemplate.setCategories(categories);
        bookTemplate.setStatus(TemplateStatusIndicator.VERIFIED);
    }
}
