package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.controller.filter.NoteFilter;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.note.NoteFullResponseDTO;
import tech.gomes.reading.management.dto.note.NoteRequestDTO;
import tech.gomes.reading.management.dto.note.NoteResponseDTO;
import tech.gomes.reading.management.dto.note.NoteResponsePageDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.NoteService;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {

    private final AuthService authService;

    private final NoteService noteService;

    @GetMapping("/")
    public ResponseEntity<NoteResponsePageDTO> getAllNotesByFilter(NoteFilter filter, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        filter.setUserId(user.getId());

        NoteResponsePageDTO responseDTO = noteService.findAllNotesByFilter(filter);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/reversed/")
    public ResponseEntity<NoteResponsePageDTO> getAllLinksToNote(@RequestParam (value = "id") long id,
                                                                 @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                 @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                 JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        NoteResponsePageDTO responseDTOS = noteService.findAllNotesThatCallTheCurrent(id, user, page, pageSize, direction);

        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/linked/")
    public ResponseEntity<NoteResponsePageDTO> getAllLinksFromNote(@RequestParam (value = "id") long id,
                                                                   @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                   @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                   JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        NoteResponsePageDTO responseDTOS = noteService.findAllNotesThatAreCalledByTheCurrent(id, user, page, pageSize, direction);

        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteFullResponseDTO> getNoteById(@PathVariable long id, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        NoteFullResponseDTO responseDTO = noteService.findNoteByIdWithSummaryLinkedNotes(id, user);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/")
    public ResponseEntity<NoteResponseDTO> createNote(JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        NoteResponseDTO responseDTO = noteService.createEmptyNote(user);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<NoteResponseDTO> updateNote(@RequestBody NoteRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        NoteResponseDTO responseDTO = noteService.updateNoteAndLinks(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable long id, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        noteService.DeleteNoteById(id, user);

        return ResponseEntity.ok(null);
    }
}
