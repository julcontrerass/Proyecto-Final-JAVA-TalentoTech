package com.techlab.service;

import com.techlab.excepciones.ProductoNoEncontradoException;
import com.techlab.productos.Producto;
import com.techlab.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Integer id, Producto productoActualizado) {
        Producto productoExistente = obtenerProductoPorId(id);

        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setStock(productoActualizado.getStock());
        productoExistente.setImagen(productoActualizado.getImagen());
        productoExistente.setCategoria(productoActualizado.getCategoria());

        return productoRepository.save(productoExistente);
    }

    public void eliminarProducto(Integer id) {
        Producto producto = obtenerProductoPorId(id);
        productoRepository.delete(producto);
    }

    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public List<Producto> obtenerProductosDisponibles() {
        return productoRepository.findProductosDisponibles();
    }

    public List<Producto> obtenerProductosConStockBajo(Integer stockMinimo) {
        return productoRepository.findProductosConStockBajo(stockMinimo);
    }

    public Producto actualizarStock(Integer id, Integer nuevoStock) {
        Producto producto = obtenerProductoPorId(id);
        producto.setStock(nuevoStock);
        return productoRepository.save(producto);
    }
}
