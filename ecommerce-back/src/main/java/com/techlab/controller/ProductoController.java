package com.techlab.controller;

import com.techlab.productos.Producto;
import com.techlab.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Integer id,
            @Valid @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarProducto(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Producto eliminado exitosamente");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProductosPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoService.buscarProductosPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> buscarProductosPorCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoService.buscarProductosPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> obtenerProductosDisponibles() {
        List<Producto> productos = productoService.obtenerProductosDisponibles();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> obtenerProductosConStockBajo(
            @RequestParam(defaultValue = "10") Integer minimo) {
        List<Producto> productos = productoService.obtenerProductosConStockBajo(minimo);
        return ResponseEntity.ok(productos);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Producto> actualizarStock(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> requestBody) {
        Integer nuevoStock = requestBody.get("stock");
        Producto productoActualizado = productoService.actualizarStock(id, nuevoStock);
        return ResponseEntity.ok(productoActualizado);
    }
}
