package com.techlab.excepciones;

public class StockInsuficienteException extends RuntimeException {

    private Integer productoId;
    private Integer cantidadSolicitada;
    private Integer stockDisponible;

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }

    public StockInsuficienteException(Integer productoId, Integer cantidadSolicitada, Integer stockDisponible) {
        super(String.format("Stock insuficiente para el producto ID %d. Solicitado: %d, Disponible: %d",
                productoId, cantidadSolicitada, stockDisponible));
        this.productoId = productoId;
        this.cantidadSolicitada = cantidadSolicitada;
        this.stockDisponible = stockDisponible;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public Integer getStockDisponible() {
        return stockDisponible;
    }
}
