package tech.gomes.reading.management.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "suggestionTemplate")
@Table(name = "TB_SUGGESTION_TEMPLATE")
public class SuggestionTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggestion_template_id")
    private Long id;

    @Column(name = "suggested_isbn")
    private String suggestedISBN;

    @Column(name = "suggested_title")
    private String suggestedTitle;

    @Column(name = "suggested_author")
    private String suggestedAuthor;

    @Column(name = "suggested_publisher")
    private String suggestedPublisher;

    @Column(name = "suggested_edition")
    private String suggestedEdition;

    @Column(name = "suggested_description")
    private String suggestedDescription;

    @Column(name = "suggested_publication_year")
    private int suggestedYear;

    @Column(name = "suggested_pages")
    private int suggestedPages;

    @Column(name = "suggested_cover")
    private String suggestedImg;

    @Column(name = "reason")
    private String reason;

    @Column(name = "justification")
    private String justification;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private TemplateStatusIndicator status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "reviewed_at")
    private Instant reviewedAt;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "TB_SUGGESTION_CATEGORY_NAMES",
            joinColumns = @JoinColumn(name = "suggestion_template_id")
    )
    @Column(name = "name")
    private Set<String> suggestedCategories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_template_id")
    private BookTemplate bookTemplate;
}
