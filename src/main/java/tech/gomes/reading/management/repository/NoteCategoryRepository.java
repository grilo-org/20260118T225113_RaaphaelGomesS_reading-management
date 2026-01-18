package tech.gomes.reading.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.NoteCategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteCategoryRepository extends JpaRepository<NoteCategory, Long> {

    Optional<NoteCategory> findByNameAndUserId(String name, long userId);

    boolean existsByNameAndUserId(String name, long userId);

    List<NoteCategory> findAllByUserId(long id);

    Optional<NoteCategory> findByIdAndUserId(long id, long userId);
}
