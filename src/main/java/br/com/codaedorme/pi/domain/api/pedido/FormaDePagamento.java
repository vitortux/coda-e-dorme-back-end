package br.com.codaedorme.pi.domain.api.pedido;

public enum FormaDePagamento {

    CARTAO("Cartão"),
    BOLETO("Boleto");

    private String tipo;

    FormaDePagamento(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
