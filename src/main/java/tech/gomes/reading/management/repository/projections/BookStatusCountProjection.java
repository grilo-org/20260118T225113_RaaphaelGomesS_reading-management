package tech.gomes.reading.management.repository.projections;

import tech.gomes.reading.management.indicator.ReadingStatusIndicator;

public interface BookStatusCountProjection {
    ReadingStatusIndicator getStatus();
    double getCount();
}
