package tech.gomes.reading.management.utils;

import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.dto.book.request.BookRequestDTO;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;

import java.time.Instant;

public class BookUtils {

    private static final Instant NOW = Instant.now();

    public static void setValuesToBookByStatusAndRequest(Book book, BookRequestDTO requestDTO) {
        ReadingStatusIndicator status = ReadingStatusIndicator.getStatusByName(requestDTO.status());

        book.setStatus(status);

        if (status == ReadingStatusIndicator.READ) {
            if (book.getStartedAt() == null && requestDTO.startedDate() == null) {
                book.setStartedAt(NOW);
            } else {
                book.setStartedAt(requestDTO.startedDate());
            }

            if (book.getFinishedAt() == null && requestDTO.finishedDate() == null) {
                book.setFinishedAt(NOW);
            } else {
                book.setFinishedAt(requestDTO.finishedDate());
            }
            book.setRating(requestDTO.rating());
            book.setReadPages(book.getBookTemplate().getPages());
        } else if (status == ReadingStatusIndicator.READING) {
            BookUtils.finishBookWhenPagesIsTheSame(book, requestDTO);

            if (book.getStartedAt() == null && requestDTO.startedDate() == null) {
                book.setStartedAt(NOW);
            } else {
                book.setStartedAt(requestDTO.startedDate());
            }

            if (book.getFinishedAt() != null && book.getFinishedAt() != NOW) {
                book.setFinishedAt(null);
            }

            book.setReadPages(requestDTO.pages());
        } else if (status == ReadingStatusIndicator.WANT_TO_READ) {
            book.setStartedAt(null);
            book.setFinishedAt(null);
        } else {
            book.setFinishedAt(null);
        }
    }

    private static void finishBookWhenPagesIsTheSame(Book book, BookRequestDTO requestDTO) {
        if (book.getBookTemplate().getPages() == requestDTO.pages()) {

            if (book.getFinishedAt() == null && requestDTO.finishedDate() == null) {
                book.setFinishedAt(NOW);
            } else {
                book.setFinishedAt(requestDTO.finishedDate());
            }

            if (book.getStartedAt() == null) {
                book.setStartedAt(NOW);
            }

            book.setStatus(ReadingStatusIndicator.READ);
            book.setReadPages(book.getBookTemplate().getPages());
        }
    }
}
