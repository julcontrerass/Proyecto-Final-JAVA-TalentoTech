package com.techlab.repository;

import com.techlab.productos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByPrecioLessThanEqual(Double precio);

    @Query("SELECT p FROM Producto p WHERE p.stock > 0")
    List<Producto> findProductosDisponibles();

    @Query("SELECT p FROM Producto p WHERE p.stock < :stockMinimo")
    List<Producto> findProductosConStockBajo(Integer stockMinimo);
}
