package br.com.codaedorme.pi.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codaedorme.pi.domain.infra.criptografia.CriptografaSenha;
import br.com.codaedorme.pi.domain.infra.validation.ValidaSenha;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private ValidaSenha validador;

	@Autowired
	private CriptografaSenha crip;

	public Usuario save(Usuario usuario, String senha2) {

		if (validador.validaSenhas(usuario.getSenha(), senha2)) {
			usuario.setSenha(crip.criptografar(usuario.getSenha()));
			return repository.save(usuario);
		} else {
			System.out.println("Senha nao compativeis!");
			return null;
		}
	}
}
