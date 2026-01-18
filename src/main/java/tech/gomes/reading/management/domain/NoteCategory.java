package tech.gomes.reading.management.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "noteCategory")
@Table(name = "TB_NOTE_CATEGORY")
public class NoteCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_category_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteCategory noteCategory = (NoteCategory) o;
        return Objects.equals(name, noteCategory.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
