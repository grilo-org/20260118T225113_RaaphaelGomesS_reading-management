package tech.gomes.reading.management.controller.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookTemplateFilter {

    private String author;
    private String ISBN;
    private String title;
    @Builder.Default
    private int page = 0;
    @Builder.Default
    private int pageSize = 10;
}
