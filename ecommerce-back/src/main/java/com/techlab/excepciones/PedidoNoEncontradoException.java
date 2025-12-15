package com.techlab.excepciones;

public class PedidoNoEncontradoException extends RuntimeException {

    private Integer pedidoId;

    public PedidoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public PedidoNoEncontradoException(Integer pedidoId) {
        super(String.format("No se encontro el pedido con ID: %d", pedidoId));
        this.pedidoId = pedidoId;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }
}
