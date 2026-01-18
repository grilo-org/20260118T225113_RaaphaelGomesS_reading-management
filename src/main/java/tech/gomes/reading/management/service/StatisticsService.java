package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.BookTemplateResponseDTOBuilder;
import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.BookCategory;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.StatisticsResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookStatusCountDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.response.CategoryFinishCountDTO;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;
import tech.gomes.reading.management.repository.BookRepository;
import tech.gomes.reading.management.repository.BookTemplateRepository;
import tech.gomes.reading.management.repository.projections.BookStatusCountProjection;
import tech.gomes.reading.management.repository.projections.CategoryFinishCountProjection;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final BookRepository bookRepository;

    private final BookTemplateRepository templateRepository;

    public StatisticsResponseDTO getStatisticsForUser(User user) {

        Double avgReadPagesPerDay = bookRepository.getAveragePagesPerDayByUserId(user.getId());
        Double avgDaysToFinish = bookRepository.getAverageReadingTimeInDaysByUserId(user.getId());
        List<BookStatusCountProjection> statusCountProjections = bookRepository.countBooksByStatusByUserId(user.getId());
        List<CategoryFinishCountProjection> finishCountProjections = bookRepository.countFinishedBooksByCategoryByUserId(user.getId());

        List<BookStatusCountDTO> statusCountList = statusCountProjections.stream()
                .map(p -> new BookStatusCountDTO(p.getStatus().getValue(), p.getCount()))
                .toList();

        List<CategoryFinishCountDTO> CategoryFinishCountList = finishCountProjections.stream()
                .map(p -> new CategoryFinishCountDTO(p.getCategory(), p.getCount()))
                .toList();

        return StatisticsResponseDTO.builder()
                .averagePagesReadInDay(avgReadPagesPerDay == null ? 0L : Math.round(avgReadPagesPerDay))
                .averageReadingTimeInDays(avgDaysToFinish == null ? 0L : Math.round(avgDaysToFinish))
                .finishedBooksByCategory(CategoryFinishCountList)
                .statusCounts(statusCountList)
                .build();
    }

    public List<BookTemplateResponseDTO> findReadRecommendationForUser(User user) {

        Book recentFinishedBook = bookRepository.findFirstByUserIdAndStatusOrderByFinishedAtDesc(user.getId(), ReadingStatusIndicator.READ).orElse(null);

        if (recentFinishedBook == null) {
            return Collections.emptyList();
        }

        Set<Long> categories = recentFinishedBook.getBookTemplate().getCategories().stream().map(BookCategory::getId).collect(Collectors.toSet());

        Pageable pageable = PageRequest.of(0, 5);

        Page<BookTemplate> recommendations = templateRepository.findSimilarTemplatesByCategories(categories, recentFinishedBook.getBookTemplate().getId(), pageable);

        return recommendations.stream().map(BookTemplateResponseDTOBuilder::from).toList();
    }
}
