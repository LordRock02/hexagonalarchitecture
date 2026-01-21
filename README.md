# API REST de Registro de Usuarios  
## Spring Boot + Arquitectura Hexagonal

Este proyecto implementa una **API REST para el registro de usuarios**, desarrollada en **Java con Spring Boot**, siguiendo el patrón de **Arquitectura Hexagonal **.


---

##  Objetivo del Proyecto

- Implementar un sistema de registro de usuarios
- Aplicar **Arquitectura Hexagonal**
- Desacoplar la lógica de negocio de frameworks y tecnologías externas


---

##  ¿Por qué Arquitectura Hexagonal?

La Arquitectura Hexagonal permite:

- Mantener el **dominio independiente** de frameworks (Spring, JPA, etc.)
- Permitir cambiar tecnologías (base de datos, API REST, mensajería, etc.) sin afectar el negocio

---

##  Capas de la Arquitectura

### Domain
- Contiene la lógica de negocio
- Define los **puertos de entrada y salida**

### Application
- Implementa los **casos de uso**
- Orquesta la lógica del dominio

### Infrastructure
- Contiene los **adaptadores**
- Expone controladores REST
- Maneja la comunicación con el exterior

---

##  Tecnologías Utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok
- Hibernate


 Configuración de la Base de Datos
1️⃣ Crear la base de datos
CREATE DATABASE hexagonal_db;

2️⃣ Configurar application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hexagonal_db
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

server.port=8080

▶️ Ejecución del Proyecto:

1️⃣ Clonar el repositorio
git clone https://github.com/tu-usuario/hexagonal-architecture.git
cd hexagonal-architecture

2️⃣ Compilar el proyecto
mvn clean install

3️⃣ Ejecutar la aplicación
mvn spring-boot:run

La aplicación quedará disponible en:

http://localhost:8080

👥 Autores

Adrian Rincon

Reoger Gomez

Pablo Sandoval
