package tech.gomes.reading.management.builder;


import org.springframework.data.domain.Page;
import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.dto.book.response.BookResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookResponsePageDTO;
import tech.gomes.reading.management.utils.ConvertUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookResponseDTOBuilder {

    public static BookResponseDTO from(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .libraryId(book.getLibrary().getId())
                .title(book.getBookTemplate().getTitle())
                .author(book.getBookTemplate().getAuthor())
                .img(ConvertUtils.uriCoverImg(book.getBookTemplate().getImg()))
                .pages(book.getReadPages())
                .rating(book.getRating())
                .status(book.getStatus().getValue())
                .totalPages(book.getBookTemplate().getPages())
                .startedDate(book.getStartedAt() == null ? null : book.getStartedAt())
                .finishedDate(book.getFinishedAt() == null ? null :book.getFinishedAt())
                .build();
    }

    public static BookResponsePageDTO from(Page<Book> bookPage) {
        List<BookResponseDTO> responseDTOList = bookPage.getContent().isEmpty() ?
                Collections.emptyList() : bookPage.getContent().stream().map(BookResponseDTOBuilder::from).collect(Collectors.toList());

        return BookResponsePageDTO.builder()
                .page(bookPage.getNumber())
                .pageSize(bookPage.getSize())
                .totalPages(bookPage.getTotalPages())
                .totalElements(bookPage.getNumberOfElements())
                .data(responseDTOList)
                .build();
    }
}
