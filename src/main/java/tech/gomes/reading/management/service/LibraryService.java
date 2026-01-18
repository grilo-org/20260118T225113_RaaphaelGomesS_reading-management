package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.LibraryResponseDTOBuilder;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.library.LibraryRequestDTO;
import tech.gomes.reading.management.dto.library.LibraryResponseDTO;
import tech.gomes.reading.management.dto.library.LibraryResponsePageDTO;
import tech.gomes.reading.management.exception.LibraryException;
import tech.gomes.reading.management.repository.LibraryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;

    public LibraryResponsePageDTO getALlLibraries(User user, int page, int pageSize, String direction) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.valueOf(direction), "updatedAt");

        Page<Library> libraryPage = libraryRepository.findByUserId(user.getId(), pageable);

        return LibraryResponseDTOBuilder.fromPage(libraryPage);
    }

    public Library getLibraryById(Long id, User user) throws Exception {
        return libraryRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new LibraryException("A biblioteca não foi encontrada.", HttpStatus.NOT_FOUND));
    }

    public LibraryResponseDTO createLibrary(LibraryRequestDTO requestDTO, User user) throws Exception {

        verifyIfLibraryAlreadyExist(requestDTO, user);

        Library newLibrary = Library
                .builder()
                .name(requestDTO.name())
                .description(requestDTO.description())
                .user(user)
                .build();

        Library library = libraryRepository.save(newLibrary);

        return LibraryResponseDTOBuilder.from(library);
    }

    public LibraryResponseDTO updateLibrary(LibraryRequestDTO requestDTO, User user) throws Exception {

        Library library = getLibraryById(requestDTO.id(), user);

        if (!requestDTO.name().equalsIgnoreCase(library.getName())) {
            verifyIfLibraryAlreadyExist(requestDTO, user);

            library.setName(requestDTO.name());
        }

        library.setDescription(requestDTO.description());

        Library updatedLibrary = libraryRepository.save(library);

        return LibraryResponseDTOBuilder.from(updatedLibrary);
    }

    public void deleteLibrary(Long id, User user) throws Exception {
        Library library = getLibraryById(id, user);

        libraryRepository.delete(library);
    }

    private void verifyIfLibraryAlreadyExist(LibraryRequestDTO requestDTO, User user) throws Exception {
        Optional<Library> optionalLibrary = libraryRepository.findByNameAndUserId(requestDTO.name(), user.getId());

        if (optionalLibrary.isPresent()) {
            throw new LibraryException("Já existe uma biblioteca com esse nome.", HttpStatus.BAD_REQUEST);
        }
    }
}
