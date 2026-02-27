# Notesfy

Aplicación web fullstack para la gestión de notas con operaciones CRUD completas. Desarrollada con **Spring Boot** en el backend y **Angular** en el frontend.

---

## Tecnologías

| Capa | Tecnología |
| --- | --- |
| Backend | Spring Boot 3.5, Java 21, Maven |
| Base de datos | MySQL 8, Hibernate / JPA |
| Frontend | Angular 20, TypeScript |
| Estilos | Bootstrap 5, SCSS |
| Utilidades | Lombok, RxJS, Spring Validation |

---

## Estructura del proyecto

```Estructura
Notesfy/
├── backend/                          # API REST con Spring Boot
│   └── src/main/java/net/ddns/deveps/notes/
│       ├── controllers/              # Controladores REST
│       ├── dto/                      # Data Transfer Objects
│       ├── entities/                 # Entidades JPA
│       ├── repositories/             # Repositorios JPA
│       └── services/                 # Lógica de negocio
│
└── frontend/                         # SPA con Angular
    └── src/app/
        ├── components/               # Componentes reutilizables
        ├── pages/                    # Páginas (Home, Listado, Crear, Editar)
        ├── services/                 # Servicios para consumir la API
        └── models/                   # Interfaces y DTOs
```

---

## Endpoints de la API

| Método | Ruta | Descripción |
| --- | --- | --- |
| `GET` | `/api/notes` | Listar todas las notas |
| `GET` | `/api/notes/{id}` | Obtener una nota por ID |
| `POST` | `/api/notes` | Crear una nueva nota |
| `PUT` | `/api/notes/{id}` | Actualizar una nota existente |
| `DELETE` | `/api/notes/{id}` | Eliminar una nota |

---

## Páginas del frontend

- **Home** — Presentación del proyecto con demo.
- **Listado de notas** — Muestra todas las notas registradas.
- **Crear nota** — Formulario para añadir una nueva nota.
- **Editar nota** — Formulario para actualizar una nota existente.
- **Eliminar nota** — Confirmación y borrado de notas.

---

## Requisitos previos

- Java 21+
- Maven
- MySQL 8 en ejecución
- Node.js 22 (LTS)
- Angular CLI 18+

---

## Puesta en marcha

### 1. Base de datos

Crea la base de datos en MySQL:

```sql
CREATE DATABASE notes_db;
```

### 2. Backend

Configura las credenciales en [backend/src/main/resources/application.properties](backend/src/main/resources/application.properties):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/notes_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

Arranca el servidor:

```bash
cd backend
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080/api/notes`.

### 3. Frontend

Instala las dependencias y levanta el servidor de desarrollo:

```bash
cd frontend
npm install
ng serve -o
```

La aplicación queda disponible en `http://localhost:4200`.

> El frontend consume la API del backend en `http://localhost:8080`. Asegúrate de que el backend esté corriendo antes de iniciar el frontend.

- [Live Demo](https://deveps.ddns.net/notes)

---

## 👨‍💻 Autor

**DevEps** - Desarrollador Full Stack

- GitHub: [github.com/devepsdev](https://github.com/devepsdev)
- Portfolio: [deveps.ddns.net](https://deveps.ddns.net)
- Email: devepsdev@gmail.com
- LinkedIn: [www.linkedin.com/in/enrique-perez-sanchez](https://www.linkedin.com/in/enrique-perez-sanchez/)

---

⭐ ¡Dale una estrella si el proyecto te ha resultado útil!
