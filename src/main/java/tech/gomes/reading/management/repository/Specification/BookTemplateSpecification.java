package tech.gomes.reading.management.repository.Specification;

import org.springframework.data.jpa.domain.Specification;
import tech.gomes.reading.management.controller.filter.BookTemplateFilter;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;

public class BookTemplateSpecification {

    private static Specification<BookTemplate> byIsbn(String isbn) {
        return (root, query, cb) ->
                isbn == null ? null : cb.equal(root.get("ISBN"), isbn);
    }

    private static Specification<BookTemplate> byAuthor(String author) {
        return (root, query, cb) ->
                author == null ? null : cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%");
    }

    private static Specification<BookTemplate> byTitle(String title) {
        return (root, query, cb) ->
                title == null ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    private static Specification<BookTemplate> byStatus() {
        return (root, query, cb) -> cb.equal(root.get("status"), TemplateStatusIndicator.VERIFIED);
    }

    public static Specification<BookTemplate> byFilter(BookTemplateFilter filter) {

        Specification<BookTemplate> spec = Specification.where(byStatus());

        Specification<BookTemplate> filters = Specification.anyOf(
                byTitle(filter.getTitle()))
                .or(byAuthor(filter.getAuthor()))
                .or(byIsbn(filter.getISBN())
                );

        return spec.and(filters);
    }
}
