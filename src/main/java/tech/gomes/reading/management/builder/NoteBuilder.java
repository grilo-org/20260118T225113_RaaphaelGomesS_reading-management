package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.domain.NoteCategory;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.note.NoteRequestDTO;
import tech.gomes.reading.management.indicator.NoteTypeIndicator;

import java.time.Instant;
import java.util.Set;

public class NoteBuilder {

    public static Note toDefaultCreate(String title, User user) {
        return Note.builder()
                .title(title)
                .user(user)
                .content("Use [[título]] para criar um link de uma outra nota à esta.")
                .type(NoteTypeIndicator.QUICK)
                .category(null)
                .book(null)
                .createdAt(Instant.now())
                .build();
    }

    public static void from(Note note, NoteRequestDTO requestDTO, Book book, NoteCategory category, Set<Note> linkedNotes) {
        note.setBook(book);
        note.setCategory(category);
        note.setTitle(requestDTO.title());
        note.setContent(requestDTO.content());
        note.setType(NoteTypeIndicator.getTypeByName(requestDTO.type()));
        note.getLinkedNotes().clear();
        note.getLinkedNotes().addAll(linkedNotes);
        note.setUpdatedAt(Instant.now());
    }
}
