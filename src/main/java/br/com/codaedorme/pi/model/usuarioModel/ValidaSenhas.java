package br.com.codaedorme.pi.model.usuarioModel;

public class ValidaSenhas {
    public boolean validaSenhas(String senha1, String senha2) {

        if (senha1.equals(senha2)) {
            return true;
        } else {
            return false;
        }
    }
}
