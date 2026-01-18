package tech.gomes.reading.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.Library;

import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

    Page<Library> findByUserId(Long userId, Pageable pageable);

    Optional<Library> findByIdAndUserId(Long id, Long userId);

    Optional<Library> findByNameAndUserId(String name, Long userId);
}
