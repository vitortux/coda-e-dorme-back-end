package br.com.codaedorme.pi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.codaedorme.pi.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}