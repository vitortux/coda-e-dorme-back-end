package br.com.codaedorme.pi.domain.api.cliente;

import org.springframework.security.core.userdetails.UserDetails;

public record LoginResponseDTO(String token, UserDetails cliente) {

}
