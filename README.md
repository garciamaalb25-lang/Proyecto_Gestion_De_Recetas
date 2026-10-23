# Proyecto Gestión de Recetas

API RESTful desarrollada en Java y Spring Boot para la gestión integral de recetas de cocina, sus categorías correspondientes y la asociación/administración de sus ingredientes.

## 📋 Tabla de Contenidos
- [Características](#-características)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Configuración](#-instalación-y-configuración)
- [Endpoints Principales](#-endpoints-principales)
- [Manejo de Errores y Validaciones](#-manejo-de-errores-y-validaciones)
- [Pruebas y Documentación](#-pruebas-y-documentación)

---

## 🚀 Características

- **Gestión de Recetas (CRUD):** Creación, lectura, actualización y borrado de recetas con atributos como nombre, descripción, tiempo de preparación, nivel de dificultad y categoría asociada.
- **Gestión de Categorías:** Administración de categorías culinarias (Postres, Sopas, etc.).
- **Gestión de Ingredientes:** Manejo de ingredientes e integración con las recetas a través de relaciones Muchos a Muchos (*Many-to-Many*).
- **Validación Avanzada:** Validaciones personalizadas de nombres únicos (`@UniqueRecetaNombre`), control del formato e i18n (internacionalización para mensajes de error).
- **Tratamiento Centralizado de Errores:** Manejador global de excepciones (`GlobalExceptionHandler`) con respuestas HTTP uniformes.
- **Poblado Inicial de Datos:** Semillero de datos automatizado mediante `DataSeed` para iniciar la aplicación con datos de prueba.

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 17+
- **Framework:** Spring Boot
  - Spring Data JPA (Hibernate)
  - Spring Web (REST)
  - Spring Validation
- **Base de Datos:** H2 Database (motor embebido/persistente)
- **Gestor de Dependencias:** Apache Maven (`mvnw`)
- **Documentación API:** Swagger / OpenAPI
- **Testing & Cliente HTTP:** Colecciones de Postman y archivos HTTP de prueba (`test.http`)

---

## 📂 Estructura del Proyecto

```text
com.gestion_de_recetas
 ├── config          # Configuraciones generales (OpenAPI, Validación)
 ├── controller      # Controladores REST (CategoriaController, RecetaController, IngredienteController)
 ├── dto             # Data Transfer Objects (Request/Response)
 ├── error           # Excepciones personalizadas y GlobalExceptionHandler
 ├── model           # Entidades JPA (Receta, Categoria, Ingrediente, Dificultad)
 ├── repo            # Repositorios JPA (Spring Data JPA)
 ├── service         # Lógica de negocio (Servicios)
 ├── utils           # Clases auxiliares y semillero de datos (DataSeed)
 └── validation      # Anotaciones y validadores personalizados (@UniqueRecetaNombre)
