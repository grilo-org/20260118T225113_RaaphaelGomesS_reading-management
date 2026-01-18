package tech.gomes.reading.management.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class UploadService {

    private final Path fileStorageLocation;

    public UploadService(@Value("${file.upload.dir}") String dir) {
        fileStorageLocation = Paths.get(dir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível criar o diretório para salvar as imagens");
        }
    }

    public String uploadCoverImg(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            try {
                if (originalFileName.contains("..")) {
                    throw new RuntimeException("O nome do arquivo contém uma sequência de caminho inválida.");
                }

                String fileExtension;
                try {
                    fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                } catch (Exception e) {
                    fileExtension = "";
                }
                String generatedFileName = UUID.randomUUID() + fileExtension;

                Path targetLocation = this.fileStorageLocation.resolve(generatedFileName);

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
                }

                log.info("Imagem salva como: {}", generatedFileName);
                return generatedFileName;
            } catch (IOException ex) {
                throw new RuntimeException("Não foi possível armazenar o arquivo.", ex);
            }
        }
        return null;
    }
}
