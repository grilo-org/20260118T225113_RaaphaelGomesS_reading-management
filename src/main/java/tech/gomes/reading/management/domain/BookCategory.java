package tech.gomes.reading.management.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bookCategory")
@Table(name = "TB_BOOK_CATEGORY")
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<BookTemplate> templateBooks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCategory bookCategory = (BookCategory) o;
        return Objects.equals(name, bookCategory.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
