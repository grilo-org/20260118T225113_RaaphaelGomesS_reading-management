package tech.gomes.reading.management.repository.projections;

import tech.gomes.reading.management.indicator.NoteTypeIndicator;

import java.time.Instant;

public interface NoteProjection {
    Long getId();
    String getTitle();
    String getCategory();
    NoteTypeIndicator getType();
    Long getBookReference();
    Instant getCreatedDate();
    Instant getUpdatedDate();
}
