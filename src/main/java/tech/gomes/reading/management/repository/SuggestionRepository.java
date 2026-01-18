package tech.gomes.reading.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;

import java.util.Optional;

@Repository
public interface SuggestionRepository extends JpaRepository<SuggestionTemplate, Long> {

    Page<SuggestionTemplate> findByStatusAndBookTemplateIsNotNull(TemplateStatusIndicator status, Pageable pageable);

    Optional<SuggestionTemplate> findByIdAndBookTemplateIsNotNull(long id);

    Optional<SuggestionTemplate> findBySuggestedISBNAndStatus(String isbn, TemplateStatusIndicator status);
}
