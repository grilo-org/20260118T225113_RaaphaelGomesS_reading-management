package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.book.request.*;
import tech.gomes.reading.management.dto.book.response.*;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.BookService;

import java.util.List;


@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthService authService;

    @GetMapping("/")
    public ResponseEntity<List<ReferenceBookDTO>> getAllUserReferencesBook(JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(bookService.findAllUserSummaryBooks(user));
    }

    @GetMapping("/reading/{id}")
    public ResponseEntity<BookResponsePageDTO> getReadingBooksInLibrary(@PathVariable long id,
                                                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                        @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                        JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponsePageDTO responseDTO = bookService.getAllBooksByStatus(id, user, ReadingStatusIndicator.READING, page, pageSize, direction);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/finished/{id}")
    public ResponseEntity<BookResponsePageDTO> getFinishedBooksInLibrary(@PathVariable long id,
                                                                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                         @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                         JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponsePageDTO responseDTO = bookService.getAllBooksByStatus(id, user, ReadingStatusIndicator.READ, page, pageSize, direction);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/awaited/{id}")
    public ResponseEntity<BookResponsePageDTO> getAwaitedBooksInLibrary(@PathVariable long id,
                                                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                        @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                        JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponsePageDTO responseDTO = bookService.getAllBooksByStatus(id, user, ReadingStatusIndicator.WANT_TO_READ, page, pageSize, direction);


        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/dropped/{id}")
    public ResponseEntity<BookResponsePageDTO> getDroppedBooksInLibrary(@PathVariable long id,
                                                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                        @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                        JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponsePageDTO responseDTO = bookService.getAllBooksByStatus(id, user, ReadingStatusIndicator.DROPPED, page, pageSize, direction);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping(value = "/", consumes = {"multipart/form-data"})
    public ResponseEntity<BookResponseDTO> registerBookInLibrary(@RequestPart("book") BookCreateRequestDTO requestDTO,
                                                                 @RequestPart(value = "coverImg", required = false) MultipartFile file,
                                                                 JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.createBook(requestDTO, user, file);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<BookResponseDTO> updateBook(@RequestBody BookRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.updateBookStatus(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<BookResponseDTO> updatePagesReadInBook(@RequestBody PagesUpdateRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.updateReadPages(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/finish")
    public ResponseEntity<BookResponseDTO> finishBook(@RequestBody FinishBookRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.finishBook(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullBookResponseDTO> getBookInformation(@PathVariable long id, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        FullBookResponseDTO responseDTO = bookService.getFullBookById(id, user);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/update/library")
    public ResponseEntity<BookResponseDTO> changeBookFromLibrary(@RequestBody ChangeLibRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.changeBookFromLibrary(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        bookService.deleteBook(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
