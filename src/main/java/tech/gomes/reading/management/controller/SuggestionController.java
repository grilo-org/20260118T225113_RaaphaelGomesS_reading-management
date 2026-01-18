package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.suggestion.request.DeclineRequestDTO;
import tech.gomes.reading.management.dto.suggestion.request.SuggestionRequestDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponsePageDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionUpdateResponseDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.SuggestionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestion")
public class SuggestionController {

    private final SuggestionService suggestionService;

    private final AuthService authService;

    @PostMapping(value = "/", consumes = {"multipart/form-data"})
    public ResponseEntity<Void> createUpdateSuggestion(@RequestPart("suggestion") SuggestionRequestDTO requestDTO,
                                                       @RequestPart(value = "coverImg", required = false) MultipartFile file,
                                                       JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        suggestionService.createUpdateSuggestion(requestDTO, user, file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<SuggestionResponsePageDTO> getAllSuggestion(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                      @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                      @RequestParam(value = "status", required = false, defaultValue = "IN_ANALYZE") String status) {

        return ResponseEntity.ok(suggestionService.findAllUpdateSuggestion(page, pageSize, direction, status));
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<SuggestionUpdateResponseDTO> getUpdateSuggestionWithOriginalTemplate(@PathVariable long id) throws Exception {

        return ResponseEntity.ok(suggestionService.findUpdateSuggestion(id));
    }

    @PostMapping("/approve/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> approveSuggestion(@PathVariable long id) throws Exception {

        suggestionService.approveSuggestion(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/decline/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> declineSuggestion(@RequestBody DeclineRequestDTO requestDTO) throws Exception {

        suggestionService.declineSuggestion(requestDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
