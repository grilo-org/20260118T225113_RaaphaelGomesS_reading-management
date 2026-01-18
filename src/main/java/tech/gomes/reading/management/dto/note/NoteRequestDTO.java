package tech.gomes.reading.management.dto.note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NoteRequestDTO(Long id,
                             Long reference,
                             String category,
                             String type,
                             @NotBlank String title,
                             @NotBlank String content,
                             Date createDate) {
}
