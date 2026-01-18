package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.builder.LibraryResponseDTOBuilder;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.library.LibraryRequestDTO;
import tech.gomes.reading.management.dto.library.LibraryResponseDTO;
import tech.gomes.reading.management.dto.library.LibraryResponsePageDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.LibraryService;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    private final AuthService authService;

    @GetMapping("/")
    public ResponseEntity<LibraryResponsePageDTO> getAllLibrariesByUserId(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                          @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                          JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(libraryService.getALlLibraries(user, page, pageSize, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryResponseDTO> findLibraryById(@PathVariable Long id, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        Library library = libraryService.getLibraryById(id, user);

        return ResponseEntity.ok(LibraryResponseDTOBuilder.from(library));
    }

    @PostMapping("/")
    public ResponseEntity<LibraryResponseDTO> createNewLibrary(@RequestBody LibraryRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        return new ResponseEntity<>(libraryService.createLibrary(requestDTO, user), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<LibraryResponseDTO> updateLibraryById(@RequestBody LibraryRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(libraryService.updateLibrary(requestDTO, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibraryById(@PathVariable Long id, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        libraryService.deleteLibrary(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
