package tech.gomes.reading.management.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bookTemplate")
@Table(name = "TB_BOOK_TEMPLATE")
public class BookTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_template_id")
    private Long id;

    @Column(name = "isbn", unique = true)
    private String ISBN;

    @Column(name = "title_author", unique = true)
    private String titleAuthor;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "edition")
    private String edition;

    @Column(name = "description")
    private String description;

    @Column(name = "publication_year")
    private int yearPublication;

    @Column(name = "pages")
    private int pages;

    @Column(name = "cover_img")
    private String img;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private TemplateStatusIndicator status;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "TB_BOOK_TEMPLATE_CATEGORY",
            joinColumns = @JoinColumn(name = "book_template_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<BookCategory> categories;
}
