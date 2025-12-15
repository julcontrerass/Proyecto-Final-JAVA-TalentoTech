package com.techlab.repository;

import com.techlab.pedidos.LineaPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaPedidoRepository extends JpaRepository<LineaPedido, Integer> {
}
