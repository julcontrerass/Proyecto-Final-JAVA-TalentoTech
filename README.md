# Proyecto Final - E-commerce TukiFeca

Sistema completo de e-commerce con frontend y backend integrados.

## Estructura del Proyecto

```
Proyecto final Backend/
├── ecommerce-back/     # Backend con Spring Boot
└── ecommerce-front/    # Frontend con HTML/CSS/JavaScript
```

## Requisitos

- Java 17 o superior
- MySQL 8.0 o superior
- Maven 3.6 o superior
- Navegador web moderno

## Instrucciones de Instalación

### 1. Configurar MySQL

1. Inicia MySQL
2. El backend creará automáticamente la base de datos `ecommerce_db`
3. Si deseas, edita la contraseña en: `ecommerce-back/src/main/resources/application.properties`

```properties
spring.datasource.password=TU_CONTRASEÑA_AQUI
```

### 2. Iniciar el Backend

```bash
cd "Proyecto final Backend/ecommerce-back"
mvn spring-boot:run
```

El backend estará disponible en: `http://localhost:8080`

### 3. Poblar la Base de Datos

Ejecuta el script SQL de datos de ejemplo:

```bash
mysql -u root -p < ecommerce-back/src/main/resources/datos-ejemplo.sql
```

O copia el contenido del archivo y ejecútalo en tu cliente MySQL.

### 4. Abrir el Frontend

1. Navega a la carpeta `ecommerce-front`
2. Abre `index.html` en tu navegador
3. O usa un servidor local como Live Server en VS Code

## Funcionalidades

### Backend (Puerto 8080)

- **GET /api/productos** - Listar todos los productos
- **GET /api/productos/{id}** - Obtener producto por ID
- **POST /api/productos** - Crear nuevo producto
- **PUT /api/productos/{id}** - Actualizar producto
- **DELETE /api/productos/{id}** - Eliminar producto
- **POST /api/pedidos** - Crear nuevo pedido
- **GET /api/pedidos** - Listar todos los pedidos
- **GET /api/pedidos/{id}** - Obtener pedido por ID

### Frontend

- Catálogo de productos con diseño al estilo Puerto Blest
- Carrito de compras funcional
- Integración completa con backend
- Validación de stock en tiempo real
- Creación de pedidos conectada a la API

## Productos Incluidos

12 cafés especiales:
- Altura 1 - House Blend ($20,000)
- Perú - Bourbon Rojo/Lavado ($22,000)
- Guatemala - Caturra/Natural ($22,000)
- Guatemala - Pacamara/Natural ($25,000)
- Nicaragua - Bourbon Caturra/Lavado ($20,000)
- Y más...

Todos en presentación de 250g.

## Diseño

El frontend de TukiFeca incluye:
- Colores: Azul oscuro (#294b92), gris claro (#f7f7f7)
- Banner de envío gratis
- Tarjetas de producto con badges
- Stock visible en cada producto

## Tecnologías Utilizadas

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- MySQL
- Maven
- Lombok

### Frontend
- HTML5
- CSS3
- JavaScript (ES6+)
- Bootstrap 5.3
- Fetch API

## Notas Importantes

1. Asegúrate de que MySQL esté ejecutándose antes de iniciar el backend
2. El backend debe estar corriendo para que el frontend funcione correctamente
3. Los productos se cargan automáticamente desde la base de datos
4. El stock se descuenta automáticamente al crear un pedido
5. Si el stock es insuficiente, se muestra un mensaje de error

## Solución de Problemas

### El frontend no muestra productos
- Verifica que el backend esté corriendo en http://localhost:8080
- Abre la consola del navegador para ver errores
- Verifica la conexión a MySQL

### Error al crear pedido
- Asegúrate de que hay suficiente stock
- Verifica que el backend esté ejecutándose
- Revisa los logs del backend para más detalles

### Error de CORS
- El backend tiene CORS habilitado para todos los orígenes
- Si persiste, verifica la configuración en `CorsConfig.java`

## Estructura del Backend

```
ecommerce-back/
├── src/main/java/com/techlab/
│   ├── productos/          # Entidades de productos
│   ├── pedidos/           # Entidades de pedidos
│   ├── excepciones/       # Excepciones personalizadas
│   ├── repository/        # Repositorios JPA
│   ├── service/           # Servicios de negocio
│   ├── controller/        # Controladores REST
│   └── config/            # Configuraciones
└── src/main/resources/
    ├── application.properties
    └── datos-ejemplo.sql
```

## Autor

Proyecto Final - E-commerce Backend con Spring Boot y MySQL
