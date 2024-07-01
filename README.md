# Microservicio de Enriquecimiento de Álbumes

## Descripción

Este microservicio en Spring Boot permite enriquecer datos de álbumes y fotos obtenidos desde un API externo, guardarlos en una base de datos en memoria H2, y exponerlos a través de endpoints REST.

## Endpoints

1. **Enriquecer y guardar álbumes**: `POST /albums/enrich-and-save`
2. **Enriquecer álbumes sin guardar**: `POST /albums/enrich`
3. **Obtener álbumes desde la base de datos**: `GET /albums`

## Tecnologías Utilizadas

- **Spring Boot**: Framework principal para la aplicación.
- **H2 Database**: Base de datos en memoria.
- **Spring Data JPA**: Abstracción de persistencia.
- **RestTemplate**: Cliente HTTP para consumir APIs externas.
- **JUnit 5**: Framework de pruebas unitarias y de integración.
- **Mockito**: Framework de código abierto para crear pruebas unitarias en Java.
- **Maven**: Gestión de dependencias.
- **MapStruct**: Herramienta que permite generar mapeos entre diferentes objetos en tiempo de compilación.
- **Springdoc**: Librería para generar el Swagger.

## Arquitectura

Se ha seguido el enfoque de **Arquitectura Hexagonal** (Puertos y Adaptadores) y **Domain-Driven Design (DDD)** para estructurar el código de manera modular y escalable.

### Paquetes

- **application**: Contiene los casos de uso (lógica de aplicación).
  - **advice**: Manejo global de excepciones usando `@ControllerAdvice`.
  - **dto**: DTOs para manejar los objetos de entrada y salida a través de los servicios REST.
  - **usecase**: Casos de uso de la aplicación.
    - **mapper**: Mappers para pasar de DTOs a Entities y viceversa.
- **domain**: Contiene las entidades de dominio, repositorios y excepciones.
  - **model**: Entidades.
  - **repository**: Interfaces para el acceso a la BBDD.
- **infrastructure**: Implementaciones de persistencia y servicios externos.
  - **controller**: Controladores REST que actúan como adaptadores de entrada.
  - **config**: Clases de configuración.
  - **external**: Services para el tratamiento de los endpoints externos.
  - **metrics**: Manejo de las métricas expuestas.


### Decisiones de Diseño

- **Arquitectura Hexagonal**: Facilita la mantenibilidad y escalabilidad al separar claramente las responsabilidades.
- **DDD**: Proporciona un enfoque claro y estructurado para modelar el dominio.
- **Persistencia en H2**: Permite realizar pruebas rápidas y sin complicaciones de configuración.
- **Mappers**: Separan la lógica de conversión entre DTOs y entidades de dominio, facilitando la mantenibilidad.
- **Arrays**: Como estructura de datos para almacenar los objetos Photo dentro de Album he utilizado arrays. Los arrays proporcionan acceso constante O(1) por índice, lo que significa que acceder a cualquier elemento dentro del array es rápido y predecible.
  Las operaciones de inserción y eliminación pueden ser costosas en términos de desplazamiento de elementos. Sin embargo, dado que la estructura parece estar diseñada para almacenar fotos relacionadas con un álbum y probablemente no se modifique con frecuencia una vez creada, esto podría no ser una desventaja significativa.
  Si el número de fotos en un álbum es generalmente manejable y predecible, un array es una elección razonable. Si la cantidad de fotos puede ser muy grande y variable, podría considerarse una estructura más dinámica como ArrayList.

## Pruebas

Se han implementado pruebas unitarias y de integración para asegurar la correcta funcionalidad del microservicio.

### Ejecutar Pruebas Unitarias

```bash
mvn test
```

### Ejecutar Test Unitarios y de Integración

```bash
mvn verify
```

## Funcionamiento

### Ejecutar la aplicación

Se hará uso de docker-compose, que a la vez que carga la imagen de la aplicación, cargará Prometheus para poder evaluar las métricas establecidas.

Desde la raíz del proyecto:

```bash
docker-compose up
```

### Ejecutar Swagger

Tras arrancar la aplicación:
```bash
http://localhost:8080/swagger-ui.html
```

Se adjunta en los resources una colección de Postman para poder realizar pruebas.