package com.techlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
        System.out.println("===========================================");
        System.out.println("TukiFeca E-commerce Backend iniciado!");
        System.out.println("API REST disponible en: http://localhost:8080");
        System.out.println("Endpoints disponibles:");
        System.out.println("  GET    /api/productos           - Listar todos los productos");
        System.out.println("  GET    /api/productos/{id}      - Obtener un producto por ID");
        System.out.println("  POST   /api/productos           - Crear un nuevo producto");
        System.out.println("  PUT    /api/productos/{id}      - Actualizar un producto");
        System.out.println("  DELETE /api/productos/{id}      - Eliminar un producto");
        System.out.println("  GET    /api/pedidos             - Listar todos los pedidos");
        System.out.println("  GET    /api/pedidos/{id}        - Obtener un pedido por ID");
        System.out.println("  POST   /api/pedidos             - Crear un nuevo pedido");
        System.out.println("  POST   /api/pedidos/{id}/confirmar - Confirmar un pedido");
        System.out.println("  POST   /api/pedidos/{id}/cancelar  - Cancelar un pedido");
        System.out.println("===========================================");
    }
}
