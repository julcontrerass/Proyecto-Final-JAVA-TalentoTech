package com.techlab.service;

import com.techlab.excepciones.PedidoNoEncontradoException;
import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.pedidos.LineaPedido;
import com.techlab.pedidos.Pedido;
import com.techlab.productos.Producto;
import com.techlab.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoService productoService;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ProductoService productoService) {
        this.pedidoRepository = pedidoRepository;
        this.productoService = productoService;
    }

    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPedidoPorId(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNoEncontradoException(id));
    }

    public Pedido crearPedido(Map<Integer, Integer> productosConCantidad) {
        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);

        for (Map.Entry<Integer, Integer> entry : productosConCantidad.entrySet()) {
            Integer productoId = entry.getKey();
            Integer cantidad = entry.getValue();

            Producto producto = productoService.obtenerProductoPorId(productoId);

            if (!producto.tieneSuficienteStock(cantidad)) {
                throw new StockInsuficienteException(
                        productoId,
                        cantidad,
                        producto.getStock()
                );
            }

            LineaPedido linea = new LineaPedido(producto, cantidad);
            pedido.agregarLinea(linea);
        }

        for (LineaPedido linea : pedido.getLineas()) {
            linea.getProducto().reducirStock(linea.getCantidad());
        }

        return pedidoRepository.save(pedido);
    }

    public Pedido crearPedidoDesdeLineas(List<LineaPedido> lineas) {
        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);

        for (LineaPedido linea : lineas) {
            Producto producto = productoService.obtenerProductoPorId(linea.getProducto().getId());

            if (!producto.tieneSuficienteStock(linea.getCantidad())) {
                throw new StockInsuficienteException(
                        producto.getId(),
                        linea.getCantidad(),
                        producto.getStock()
                );
            }

            LineaPedido nuevaLinea = new LineaPedido(producto, linea.getCantidad());
            pedido.agregarLinea(nuevaLinea);
        }

        for (LineaPedido linea : pedido.getLineas()) {
            linea.getProducto().reducirStock(linea.getCantidad());
        }

        return pedidoRepository.save(pedido);
    }

    public Pedido actualizarEstadoPedido(Integer id, Pedido.EstadoPedido nuevoEstado) {
        Pedido pedido = obtenerPedidoPorId(id);
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }

    public Pedido cancelarPedido(Integer id) {
        Pedido pedido = obtenerPedidoPorId(id);

        if (pedido.getEstado() == Pedido.EstadoPedido.CANCELADO) {
            throw new IllegalStateException("El pedido ya esta cancelado");
        }

        if (pedido.getEstado() == Pedido.EstadoPedido.ENTREGADO) {
            throw new IllegalStateException("No se puede cancelar un pedido ya entregado");
        }

        for (LineaPedido linea : pedido.getLineas()) {
            linea.getProducto().incrementarStock(linea.getCantidad());
        }

        pedido.setEstado(Pedido.EstadoPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    public Pedido confirmarPedido(Integer id) {
        return actualizarEstadoPedido(id, Pedido.EstadoPedido.CONFIRMADO);
    }

    public List<Pedido> buscarPedidosPorEstado(Pedido.EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public List<Pedido> obtenerPedidosEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pedidoRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    public Double calcularTotalVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Double total = pedidoRepository.calcularTotalVentas(fechaInicio, fechaFin);
        return total != null ? total : 0.0;
    }

    public List<Pedido> obtenerPedidosOrdenados() {
        return pedidoRepository.findAllOrderByFechaDesc();
    }
}
