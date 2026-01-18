package tech.gomes.reading.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;
import tech.gomes.reading.management.repository.projections.BookStatusCountProjection;
import tech.gomes.reading.management.repository.projections.CategoryFinishCountProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByUserId(long userId);

    Optional<Book> findByBookTemplateIdAndUserId(long templateId, long userId);

    Optional<Book> findByIdAndUserId(long id, long userId);

    Page<Book> findAllByLibraryIdAndStatus(long id, ReadingStatusIndicator status, Pageable pageable);

    @Query(value = "SELECT AVG(bt.pages / (EXTRACT(DAY FROM (b.finished_at - b.started_at)) + 1)) " +
            "FROM TB_BOOK b " +
            "JOIN TB_BOOK_TEMPLATE bt ON b.book_template_id = bt.book_template_id " +
            "WHERE b.user_id = :userId " +
            "  AND b.status = 'READ' " +
            "  AND b.started_at IS NOT NULL " +
            "  AND b.finished_at IS NOT NULL " +
            "  AND b.finished_at >= b.started_at",
            nativeQuery = true)
    Double getAveragePagesPerDayByUserId(@Param("userId") long userId);

    @Query(value = "SELECT AVG(EXTRACT(DAY FROM (finished_at - started_at))) " +
            "FROM TB_BOOK " +
            "WHERE user_id = :userId AND status = 'READ' " +
            "AND started_at IS NOT NULL AND finished_at IS NOT NULL",
            nativeQuery = true)
    Double getAverageReadingTimeInDaysByUserId(@Param("userId") long userId);

    @Query("SELECT b.status as status, COUNT(b) as count " +
            "FROM book b WHERE b.user.id = :userId GROUP BY b.status " +
            "ORDER BY count DESC")
    List<BookStatusCountProjection> countBooksByStatusByUserId(@Param("userId") long userId);

    @Query("SELECT c.name as category, COUNT(b) as count " +
            "FROM book b " +
            "JOIN b.bookTemplate bt " +
            "JOIN bt.categories c " +
            "WHERE b.user.id = :userId AND b.status = 'READ' " +
            "GROUP BY c.name " +
            "ORDER BY count DESC LIMIT 5")
    List<CategoryFinishCountProjection> countFinishedBooksByCategoryByUserId(@Param("userId") long userId);

    Optional<Book> findFirstByUserIdAndStatusOrderByFinishedAtDesc(Long userId, ReadingStatusIndicator status);
}
