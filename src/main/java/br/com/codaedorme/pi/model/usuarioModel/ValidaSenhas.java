package br.com.codaedorme.pi.model.usuarioModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.codaedorme.pi.model.Usuario;
import br.com.codaedorme.pi.repository.UsuarioRepository;

@Service
public class ValidaSenhas {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean validaSenhas(String senha1, String senha2) {

        if (senha1.equals(senha2)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validaHash(String senhaRecebida, Long usuarioID) {
        Usuario usuario = usuarioRepository.findById(usuarioID).orElse(null);

        if (usuario == null) {
            return false;
        }

        String senhaCriptografada = usuario.getSenha();

        return BCrypt.verifyer().verify(senhaRecebida.toCharArray(), senhaCriptografada.getBytes()).verified;
    }
}
