package tech.gomes.reading.management.builder;

import org.springframework.data.domain.Page;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.dto.library.LibraryResponseDTO;
import tech.gomes.reading.management.dto.library.LibraryResponsePageDTO;

import java.util.Collections;
import java.util.List;

public class LibraryResponseDTOBuilder {

    public static LibraryResponsePageDTO fromPage(Page<Library> libraries) {

        List<LibraryResponseDTO> responseDTOList = libraries.getContent().isEmpty() ?
                Collections.emptyList() : libraries.getContent().stream().map(LibraryResponseDTOBuilder::from).toList();

        return LibraryResponsePageDTO.builder()
                .page(libraries.getNumber())
                .pageSize(libraries.getSize())
                .totalPages(libraries.getTotalPages())
                .totalElements(libraries.getNumberOfElements())
                .libraries(responseDTOList)
                .build();
    }

    public static LibraryResponseDTO from(Library library) {
        return LibraryResponseDTO.builder()
                .id(library.getId())
                .name(library.getName())
                .description(library.getDescription())
                .build();
    }
}
