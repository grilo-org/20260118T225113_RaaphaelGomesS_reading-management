package tech.gomes.reading.management.controller.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteFilter {

    private String title;
    private Long categoryId;
    private Long bookId;
    private Long userId;
    private String type;
    @Builder.Default
    private int page = 0;
    @Builder.Default
    private int pageSize = 10;
}
