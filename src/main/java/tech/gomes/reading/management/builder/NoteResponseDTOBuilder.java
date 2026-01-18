package tech.gomes.reading.management.builder;

import org.springframework.data.domain.Page;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.dto.note.*;
import tech.gomes.reading.management.repository.projections.NoteProjection;
import tech.gomes.reading.management.repository.projections.NoteSummaryProjection;
import tech.gomes.reading.management.utils.DateUtils;

import java.util.Collections;
import java.util.List;

public class NoteResponseDTOBuilder {

    public static NoteFullResponseDTO from(Note note, List<NoteSummaryProjection> linkedNotes) {

        List<NoteSummaryDTO> linkedNotesSummary = linkedNotes.stream().map(p -> new NoteSummaryDTO(p.getId(), p.getTitle())).toList();

        return NoteFullResponseDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .type(note.getType() == null ? null : note.getType().getValue())
                .category(note.getCategory() == null ? null : note.getCategory().getName())
                .bookReference(note.getBook() == null ? null : note.getBook().getId())
                .content(note.getContent())
                .createdDate(DateUtils.formatInstantToDateTime(note.getCreatedAt()))
                .linkedNotes(linkedNotesSummary)
                .build();
    }

    public static NoteResponseDTO from(Note note) {
        return NoteResponseDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .bookReference(note.getBook() == null ? null : note.getBook().getId())
                .category(note.getCategory() == null ? null : note.getCategory().getName())
                .type(note.getType() == null ? null : note.getType().getValue())
                .createdDate(DateUtils.formatInstantToDateTime(note.getCreatedAt()))
                .updatedDate(DateUtils.formatInstantToDateTime(note.getUpdatedAt()))
                .build();
    }

    public static NoteResponseDTO from(NoteProjection note) {
        return NoteResponseDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .bookReference(note.getBookReference())
                .category(note.getCategory())
                .type(note.getType().name())
                .createdDate(DateUtils.formatInstantToDateTime(note.getCreatedDate()))
                .updatedDate(DateUtils.formatInstantToDateTime(note.getUpdatedDate()))
                .build();
    }

    public static NoteResponsePageDTO from(Page<NoteProjection> notePage) {

        List<NoteResponseDTO> responseDTOList = notePage.getContent().isEmpty() ?
                Collections.emptyList() : notePage.getContent().stream().map(NoteResponseDTOBuilder::from).toList();

        return NoteResponsePageDTO.builder()
                .page(notePage.getNumber())
                .pageSize(notePage.getSize())
                .totalPages(notePage.getTotalPages())
                .totalElements(notePage.getNumberOfElements())
                .data(responseDTOList)
                .build();
    }

    public static NoteResponsePageDTO fromPage(Page<Note> notePage) {

        List<NoteResponseDTO> responseDTOList = notePage.getContent().isEmpty() ?
                Collections.emptyList() : notePage.getContent().stream().map(NoteResponseDTOBuilder::from).toList();

        return NoteResponsePageDTO.builder()
                .page(notePage.getNumber())
                .pageSize(notePage.getSize())
                .totalPages(notePage.getTotalPages())
                .totalElements(notePage.getNumberOfElements())
                .data(responseDTOList)
                .build();
    }
}
