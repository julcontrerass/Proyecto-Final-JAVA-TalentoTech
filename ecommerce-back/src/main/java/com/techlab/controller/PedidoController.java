package com.techlab.controller;

import com.techlab.pedidos.LineaPedido;
import com.techlab.pedidos.Pedido;
import com.techlab.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Integer id) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Map<String, Map<String, Integer>> request) {
        Map<String, Integer> productosStr = request.get("productos");
        Map<Integer, Integer> productos = new HashMap<>();

        for (Map.Entry<String, Integer> entry : productosStr.entrySet()) {
            try {
                Integer productoId = Integer.parseInt(entry.getKey());
                productos.put(productoId, entry.getValue());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID de producto invalido: " + entry.getKey());
            }
        }

        Pedido nuevoPedido = pedidoService.crearPedido(productos);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @PostMapping("/lineas")
    public ResponseEntity<Pedido> crearPedidoDesdeLineas(
            @RequestBody Map<String, List<LineaPedido>> request) {
        List<LineaPedido> lineas = request.get("lineas");
        Pedido nuevoPedido = pedidoService.crearPedidoDesdeLineas(lineas);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstadoPedido(
            @PathVariable Integer id,
            @RequestBody Map<String, String> request) {
        String estadoStr = request.get("estado");
        Pedido.EstadoPedido nuevoEstado = Pedido.EstadoPedido.valueOf(estadoStr.toUpperCase());
        Pedido pedidoActualizado = pedidoService.actualizarEstadoPedido(id, nuevoEstado);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<Pedido> confirmarPedido(@PathVariable Integer id) {
        Pedido pedidoConfirmado = pedidoService.confirmarPedido(id);
        return ResponseEntity.ok(pedidoConfirmado);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable Integer id) {
        Pedido pedidoCancelado = pedidoService.cancelarPedido(id);
        return ResponseEntity.ok(pedidoCancelado);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> buscarPedidosPorEstado(@PathVariable String estado) {
        Pedido.EstadoPedido estadoPedido = Pedido.EstadoPedido.valueOf(estado.toUpperCase());
        List<Pedido> pedidos = pedidoService.buscarPedidosPorEstado(estadoPedido);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<Pedido>> obtenerPedidosEntreFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosEntreFechas(inicio, fin);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/ordenados")
    public ResponseEntity<List<Pedido>> obtenerPedidosOrdenados() {
        List<Pedido> pedidos = pedidoService.obtenerPedidosOrdenados();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/ventas")
    public ResponseEntity<Map<String, Double>> calcularTotalVentas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        Double total = pedidoService.calcularTotalVentas(inicio, fin);
        Map<String, Double> response = new HashMap<>();
        response.put("totalVentas", total);
        return ResponseEntity.ok(response);
    }
}
