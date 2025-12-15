package com.techlab.productos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Clase que representa un producto en el sistema de e-commerce.
 * Utiliza anotaciones JPA para mapeo a base de datos y Lombok para reducir boilerplate.
 */
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del producto no puede estar vacio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacia")
    @Size(min = 10, max = 500, message = "La descripcion debe tener entre 10 y 500 caracteres")
    @Column(nullable = false, length = 500)
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

    @NotBlank(message = "La URL de la imagen no puede estar vacia")
    @Column(nullable = false)
    private String imagen;

    @Column(nullable = false)
    private String categoria;

    /**
     * Valida si hay suficiente stock para una cantidad solicitada.
     * @param cantidad cantidad a validar
     * @return true si hay stock suficiente, false en caso contrario
     */
    public boolean tieneSuficienteStock(int cantidad) {
        return this.stock >= cantidad;
    }

    /**
     * Reduce el stock del producto.
     * @param cantidad cantidad a reducir del stock
     */
    public void reducirStock(int cantidad) {
        if (cantidad > this.stock) {
            throw new IllegalArgumentException("No hay suficiente stock disponible");
        }
        this.stock -= cantidad;
    }

    /**
     * Incrementa el stock del producto.
     * @param cantidad cantidad a agregar al stock
     */
    public void incrementarStock(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        this.stock += cantidad;
    }

    /**
     * Calcula el precio total para una cantidad dada.
     * @param cantidad cantidad de productos
     * @return precio total
     */
    public double calcularPrecioTotal(int cantidad) {
        return this.precio * cantidad;
    }
}
