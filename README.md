# Notesfy

Aplicación web fullstack para la gestión de notas. Desarrollada con **Spring Boot** en el backend y **Angular** en el frontend, con autenticación JWT, gestión de categorías, búsqueda con filtros combinados y paginación.

---

## Tecnologías

| Capa | Tecnología |
| --- | --- |
| Backend | Spring Boot 3.5, Java 21, Maven |
| Base de datos | MySQL 8, Hibernate / JPA |
| Seguridad | Spring Security, JWT (jjwt 0.11.5), BCrypt |
| Frontend | Angular 20, TypeScript |
| Estilos | Bootstrap 5, SCSS |
| Utilidades | Lombok, RxJS, Spring Validation |

---

## Características

### Autenticación JWT

- Registro e inicio de sesión con token JWT almacenado en `localStorage`
- Interceptor HTTP funcional que inyecta el header `Authorization: Bearer <token>` en cada petición
- Guard funcional que protege las rutas privadas y redirige a `/login` si el token ha expirado
- Contraseñas hasheadas con BCrypt

### Gestión de notas

- CRUD completo: crear, leer, actualizar y eliminar notas
- Cada nota incluye título, contenido, estado de completada y categoría opcional
- Las notas son privadas por usuario (filtradas en base de datos)
- Confirmación antes de eliminar

### Categorías

- CRUD completo de categorías con nombre y color personalizado (color picker)
- Las categorías se muestran como badges de color en el listado de notas

### Búsqueda y filtros

- Búsqueda por texto en título y contenido con debounce de 300 ms (RxJS)
- Filtro por categoría y rango de fechas
- Todos los filtros son opcionales y combinables (Spring Data JPA Specifications)

### Paginación y ordenación

- Paginación del lado del servidor con `Page<T>` de Spring Data
- Componente `PaginationComponent` reutilizable con ellipsis
- Ordenación por fecha de modificación, creación o título (asc/desc)

### Formularios reactivos

- Todos los formularios usan `ReactiveFormsModule` (FormGroup, FormControl, Validators)
- Mensajes de error inline debajo de cada campo cuando es inválido y tocado
- Botón de submit deshabilitado mientras el formulario es inválido

---

## Estructura del proyecto

```text
Notesfy/
├── backend/                          # API REST con Spring Boot
│   └── src/main/java/net/ddns/deveps/notes/
│       ├── controllers/              # AuthController, NoteController, CategoryController
│       ├── dto/                      # DTOs de request y response
│       ├── entities/                 # Note, User, UserRole, Category
│       ├── repositories/             # Repositorios JPA + NoteSpecification
│       ├── security/                 # JwtUtil, JwtAuthenticationFilter, SecurityConfig
│       └── services/                 # Lógica de negocio (interfaces + implementaciones)
│
└── frontend/                         # SPA con Angular 20 (standalone components)
    └── src/app/
        ├── components/               # Header, NoteCard, NoteForm, Pagination
        ├── guards/                   # authGuard (funcional)
        ├── interceptors/             # authInterceptor (funcional)
        ├── models/                   # Note, User, AuthResponse, Category, PageResponse<T>
        ├── pages/                    # Home, Login, Register, Notes, Categories
        └── services/                 # AuthService, NoteService, CategoryService
```

---

## Endpoints de la API

### Autenticación (público)

| Método | Ruta | Descripción |
| --- | --- | --- |
| `POST` | `/api/auth/register` | Registrar un nuevo usuario |
| `POST` | `/api/auth/login` | Iniciar sesión, devuelve JWT |

### Notas (requiere JWT)

| Método | Ruta | Descripción |
| --- | --- | --- |
| `GET` | `/api/notes` | Listar notas del usuario (paginado) |
| `GET` | `/api/notes/search` | Buscar con filtros combinados (paginado) |
| `GET` | `/api/notes/{id}` | Obtener una nota por ID |
| `POST` | `/api/notes` | Crear una nueva nota |
| `PUT` | `/api/notes/{id}` | Actualizar una nota existente |
| `DELETE` | `/api/notes/{id}` | Eliminar una nota |

### Categorías (requiere JWT)

| Método | Ruta | Descripción |
| --- | --- | --- |
| `GET` | `/api/categories` | Listar categorías del usuario |
| `POST` | `/api/categories` | Crear una nueva categoría |
| `PUT` | `/api/categories/{id}` | Actualizar una categoría |
| `DELETE` | `/api/categories/{id}` | Eliminar una categoría |

---

## Páginas del frontend

- **Home** — Presentación del proyecto.
- **Login** — Formulario reactivo de inicio de sesión.
- **Register** — Formulario reactivo de registro.
- **Notas** — Listado con búsqueda, filtros, paginación y formulario de creación.
- **Categorías** — Gestión de categorías con color picker.

---

## Requisitos previos

- Java 21+
- Maven
- MySQL 8 en ejecución
- Node.js 22 (LTS)

---

## Puesta en marcha

### 1. Base de datos

```sql
CREATE DATABASE notes_db;
```

### 2. Backend

Copia el archivo de ejemplo y rellena tus credenciales:

```bash
cp backend/.env.example backend/.env
```

```properties
# backend/.env
DB_URL=jdbc:mysql://localhost:3306/notes_db
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña
JWT_SECRET=genera_un_secreto_base64_de_256_bits
JWT_EXPIRATION=86400000
```

> El archivo `.env` es ignorado por git. Nunca lo subas al repositorio.

Arranca el servidor:

```bash
cd backend
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

### 3. Frontend

```bash
cd frontend
npm install
node_modules/.bin/ng serve -o
```

La aplicación queda disponible en `http://localhost:4200`.

> El frontend apunta al backend en `http://localhost:8080`. Asegúrate de que el backend esté corriendo antes de iniciar el frontend.

---

## 👨‍💻 Autor

**DevEps** - Desarrollador Full Stack

- GitHub: [github.com/devepsdev](https://github.com/devepsdev)
- Portfolio: [deveps.ddns.net](https://deveps.ddns.net)
- Email: devepsdev@gmail.com
- LinkedIn: [linkedin.com/in/enrique-perez-sanchez](https://www.linkedin.com/in/enrique-perez-sanchez/)

---

⭐ ¡Dale una estrella si el proyecto te ha resultado útil!
