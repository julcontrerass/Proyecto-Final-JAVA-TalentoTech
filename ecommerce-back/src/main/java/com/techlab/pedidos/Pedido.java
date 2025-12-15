package com.techlab.pedidos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un pedido en el sistema de e-commerce.
 * Contiene una lista de lineas de pedido y calcula el total automaticamente.
 */
@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty(message = "El pedido debe tener al menos un producto")
    @Builder.Default
    private List<LineaPedido> lineas = new ArrayList<>();

    @Column(nullable = false)
    private Double total;

    public enum EstadoPedido {
        PENDIENTE,
        CONFIRMADO,
        ENVIADO,
        ENTREGADO,
        CANCELADO
    }

    public double calcularTotal() {
        return lineas.stream()
                .mapToDouble(LineaPedido::calcularSubtotal)
                .sum();
    }

    public void agregarLinea(LineaPedido linea) {
        lineas.add(linea);
        linea.setPedido(this);
        actualizarTotal();
    }

    public void eliminarLinea(LineaPedido linea) {
        lineas.remove(linea);
        linea.setPedido(null);
        actualizarTotal();
    }

    public void actualizarTotal() {
        this.total = calcularTotal();
    }

    @PrePersist
    protected void onCreate() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
        if (estado == null) {
            estado = EstadoPedido.PENDIENTE;
        }
        actualizarTotal();
    }

    @PreUpdate
    protected void onUpdate() {
        actualizarTotal();
    }
}
