package tech.gomes.reading.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.repository.projections.NoteProjection;
import tech.gomes.reading.management.repository.projections.NoteSummaryProjection;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {

    Optional<Note> findByIdAndUserId(long id, long userId);

    Set<Note> findAllByTitleInAndUserId(Set<String> titles, long id);

    boolean existsByTitleAndUserId(String title, long id);

    @Query("SELECT target.id AS id, target.title AS title FROM note source JOIN source.linkedNotes target WHERE source.id = :sourceNoteId")
    List<NoteSummaryProjection> findAllSummaryTargetNotes(@Param("sourceNoteId") long id);

    @Query("SELECT target.id AS id, target.title AS title, target.category.name AS category, target.type AS type, target.book.id AS bookReference, target.createdAt AS createdDate, target.updatedAt AS updatedDate FROM note source JOIN source.linkedNotes target WHERE source.id = :sourceNoteId")
    Page<NoteProjection> findAllLinkedNotesByNoteId(@Param("sourceNoteId") long id, Pageable pageable);

    @Query("SELECT source.id AS id, source.title AS title, source.category.name AS category, source.type AS type, source.book.id AS bookReference, source.createdAt AS createdDate, source.updatedAt AS updatedDate FROM note target JOIN target.invertedNoteLinks source WHERE target.id = :targetNoteId")
    Page<NoteProjection> findAllNotesLinkingToNoteId(@Param("targetNoteId") long id, Pageable pageable);
}
