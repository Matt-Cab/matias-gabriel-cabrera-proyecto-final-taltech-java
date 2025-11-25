# Proyecto de entrega final para el curso de Backend con Java de Talento Tech

Este proyecto es una aplicación CRUD básica para la gestión de productos
utilizando **Spring Boot**, con una arquitectura en capas (Controller,
Service, Repository y Model).

## Características principales

-   Crear, listar, actualizar y eliminar productos.
-   Validación de stock mediante una excepción personalizada
    (`InsufficientStockException`).
-   Persistencia en memoria mediante `JpaRepository` (H2 o estructura en
    memoria según configuración).
-   Arquitectura separada por capas.

------------------------------------------------------------------------

## Estructura del Proyecto

    src/main/java
     └── com.example.product
          ├── controller
          │     └── ProductController.java
          ├── service
          │     └── ProductService.java
          ├── repository
          │     └── ProductRepository.java
          ├── model
          │     └── Product.java
          ├── exceptions
          │     └── InsufficientStockException.java
          └── ProductApplication.java

------------------------------------------------------------------------

## Capas del Proyecto

### Modelo (Model)

**`Product.java`** Define la entidad Producto con atributos como: -
`id` - `name` - `price` - `stock`

**`InsufficientStockException.java`**\
Excepción personalizada lanzada cuando se intenta vender o actualizar
más cantidad de la disponible.

------------------------------------------------------------------------

### Repositorio (Repository)

**`ProductRepository.java`**\
Extiende `JpaRepository` para proveer las operaciones CRUD
automáticamente.

------------------------------------------------------------------------

### Servicio (Service)

**`ProductService.java`**\
Contiene la lógica de negocio: - Maneja el control de stock. -
Interactúa con el repositorio. - Lanza excepciones cuando corresponde.

------------------------------------------------------------------------

### Controlador (Controller)

**`ProductController.java`**\
Exponen los endpoints REST:


| Método   | Endpoint         | Descripción                      |
| :------- | :------:         | -------:                         |
| GET      | `/products`      | Lista todos los productos        |
| GET      | `/products/{id}` | Obtiene un producto por ID       |
| POST     | `/products`      | Crea un nuevo producto           |
| PUT      | `/products/{id}` | Actualiza un producto existente  |
| DELETE   | `/products/{id}` | Elimina un producto              |

------------------------------------------------------------------------

## Ejecución del Proyecto

1.  Clonar el repositorio
2.  Asegurarse de tener **Java 17+** y **Maven** instalados\
3.  Ejecutar


La API estará disponible en:

    http://localhost:8080/products

------------------------------------------------------------------------

## Ejemplos de Requests

### Crear un producto (POST)

``` json
{
  "name": "Laptop",
  "price": 1500.0,
  "stock": 10
}
```

### Actualizar stock (PUT)

``` json
{
  "name": "Laptop",
  "price": 1500.0,
  "stock": 5
}
```

------------------------------------------------------------------------

## Sobre el desarrollador

Desarrollado por Matias Gabriel Cabrera para la entrega del trabajo final de taltech Bakcend JAVA