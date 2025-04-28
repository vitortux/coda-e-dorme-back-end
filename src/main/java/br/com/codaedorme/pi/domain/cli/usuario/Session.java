package br.com.codaedorme.pi.domain.cli.usuario;

import org.springframework.stereotype.Component;

@Component
public class Session {

    private Usuario usuarioLogado;

    public void setUsuario(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public Usuario getUsuario() {
        return this.usuarioLogado;
    }

    public void logout() {
        this.usuarioLogado = null;
    }

}
