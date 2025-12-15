package com.techlab.repository;

import com.techlab.pedidos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByEstado(Pedido.EstadoPedido estado);

    List<Pedido> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<Pedido> findByTotalGreaterThanEqual(Double total);

    @Query("SELECT p FROM Pedido p ORDER BY p.fecha DESC")
    List<Pedido> findAllOrderByFechaDesc();

    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.fecha BETWEEN :fechaInicio AND :fechaFin AND p.estado != 'CANCELADO'")
    Double calcularTotalVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
