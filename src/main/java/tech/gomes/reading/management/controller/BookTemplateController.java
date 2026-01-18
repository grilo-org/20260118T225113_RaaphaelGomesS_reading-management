package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.builder.BookTemplateResponseDTOBuilder;
import tech.gomes.reading.management.controller.filter.BookTemplateFilter;
import tech.gomes.reading.management.dto.book.request.BookTemplateRequestDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponsePageDTO;
import tech.gomes.reading.management.service.BookTemplateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/template")
public class BookTemplateController {

    private final BookTemplateService templateService;

    @GetMapping("/search")
    public ResponseEntity<BookTemplateResponsePageDTO> searchTemplateByFilter(BookTemplateFilter filter) {

        return ResponseEntity.ok(templateService.findAllTemplatesByFilter(filter));
    }

    @GetMapping("/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookTemplateResponsePageDTO> getAllTemplatesByStatus(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                               @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                               @RequestParam(value = "status", required = false, defaultValue = "IN_ANALYZE") String status) {

        return ResponseEntity.ok(templateService.findAllTemplatesByStatus(page, pageSize, direction, status));
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookTemplateResponseDTO> getTemplateToBeAnalyze(@PathVariable long id) throws Exception {

        return ResponseEntity.ok(BookTemplateResponseDTOBuilder.from(templateService.findTemplateByIdWithAnyStatus(id)));
    }

    @PutMapping(value = "/fix", consumes = {"multipart/form-data"})
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookTemplateResponseDTO> updateBookTemplate(@RequestPart("template") BookTemplateRequestDTO requestDTO,
                                                                      @RequestPart("coverImg") MultipartFile file) throws Exception {

        BookTemplateResponseDTO responseDTO = templateService.updateBookTemplateByAdminRequest(requestDTO, file);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/inactive/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> inactiveInvalidTemplate(@PathVariable long id) throws Exception {

        templateService.inactiveInvalidTemplate(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/approve/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> approveTemplate(@PathVariable long id) throws Exception {

        templateService.approveTemplate(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

