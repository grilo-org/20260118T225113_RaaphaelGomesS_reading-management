package tech.gomes.reading.management.dto.auth;

import java.time.Instant;

public record LoginResponseDTO(String token, Instant expiration) {
}
