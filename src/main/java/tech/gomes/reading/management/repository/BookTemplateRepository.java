package tech.gomes.reading.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BookTemplateRepository extends JpaRepository<BookTemplate, Long>, JpaSpecificationExecutor<BookTemplate> {

    @Query("SELECT b FROM bookTemplate b WHERE (b.ISBN = :identifier OR b.titleAuthor = :identifier) AND b.status <> 'INACTIVE'")
    Optional<BookTemplate> findByIdentifierWhenNotIsInactive(@Param("identifier") String identifier);

    Page<BookTemplate> findByStatus(TemplateStatusIndicator status, Pageable pageable);

    Optional<BookTemplate> findByIdAndStatus(long id, TemplateStatusIndicator status);

    Optional<BookTemplate> findById(long id);

    @Query("SELECT bt FROM bookTemplate bt JOIN bt.categories c WHERE c.id IN :categoryIds " +
            "AND bt.id != :targetTemplateId AND bt.status = 'VERIFIED' " +
            "GROUP BY bt.id ORDER BY COUNT(bt.id) DESC"
    )
    Page<BookTemplate> findSimilarTemplatesByCategories(@Param("categoryIds") Set<Long> categoryIds,
                                                        @Param("targetTemplateId") long targetTemplateId,
                                                        Pageable pageable);
}
