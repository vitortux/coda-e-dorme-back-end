package br.com.codaedorme.pi.domain.api.pedido;

public enum StatusPedido {
    AGUARDANDO_PAGAMENTO,
    PAGAMENTO_REJEITADO,
    PAGAMENTO_COM_SUCESSO,
    AGUARDANDO_RETIRADA,
    EM_TRANSITO,
    ENTREGUE
}
