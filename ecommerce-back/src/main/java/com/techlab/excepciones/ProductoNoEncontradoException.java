package com.techlab.excepciones;

public class ProductoNoEncontradoException extends RuntimeException {

    private Integer productoId;

    public ProductoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public ProductoNoEncontradoException(Integer productoId) {
        super(String.format("No se encontro el producto con ID: %d", productoId));
        this.productoId = productoId;
    }

    public Integer getProductoId() {
        return productoId;
    }
}
