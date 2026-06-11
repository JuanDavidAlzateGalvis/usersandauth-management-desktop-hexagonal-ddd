# Bibliography Management System — Hexagonal Architecture

> **Asignatura:** Desarrollo de Software  
> **Programa:** Ingeniería del Software — Universidad de Cartagena  
> **Tutor:** John Carlos Arrieta Arrieta  
> **Estudiante:** Juan David Alzate Galvis  
> **CEA:** Ejercicio 21 — Bibliografía de Investigadores

Sistema de gestión de autores e investigadores desarrollado en Java con **Arquitectura Hexagonal**, **DDD** y **SOLID**, con persistencia en MySQL y interfaz de consola interactiva.

---

## Requisitos previos

Antes de clonar y ejecutar el proyecto, asegúrate de tener instalado:

| Herramienta | Versión recomendada | Descarga |
|---|---|---|
| JDK | 21 LTS (Temurin) | [adoptium.net](https://adoptium.net) |
| Maven | 3.9+ | Incluido en NetBeans/IntelliJ |
| MySQL | 8.0+ | [Laragon](https://laragon.org) (recomendado) |
| Git | Cualquiera | [git-scm.com](https://git-scm.com) |

> ⚠️ **Importante:** El proyecto **no es compatible con JDK 25** debido a una limitación conocida de Lombok. Usa JDK 21 LTS obligatoriamente.

---

##  Instalación y configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/JuanDavidAlzateGalvis/usersandauth-management-desktop-hexagonal-ddd.git
cd usersandauth-management-desktop-hexagonal-ddd
```

### 2. Configurar la base de datos

Inicia MySQL (con Laragon: **Start All**) y ejecuta el siguiente script desde HeidiSQL o la terminal de MySQL:

```sql
CREATE DATABASE IF NOT EXISTS crud_usuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crud_usuarios;

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED') NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS authors (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    full_name   VARCHAR(100) NOT NULL,
    work_center VARCHAR(150) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Usuario administrador inicial (password: Admin1234!)
INSERT INTO users (id, name, email, password, role, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@example.com',
    '$2a$12$pOeQQbTaBLwpBPTkTlGpBuGekFvD3NWgPG7t7rqUGUhtNBOHgcEry',
    'ADMIN',
    'ACTIVE'
);
```

### 3. Configurar application.properties

Edita el archivo `src/main/resources/application.properties` con tus credenciales de MySQL:

```properties
db.host=localhost
db.port=3306
db.name=crud_usuarios
db.username=root
db.password=
```

> Si tu MySQL tiene contraseña, agrégala en `db.password=tucontraseña`

### 4. Configurar el IDE

**IntelliJ IDEA:**
1. `File → Project Structure → Project`
2. Cambia el **SDK** a **JDK 21**
3. Cambia el **Language Level** a **21**
4. `Build → Rebuild Project`

**NetBeans:**
1. `Tools → Java Platforms → Add Platform` → navega a la carpeta del JDK 21
2. Clic derecho en el proyecto → `Properties → Libraries → Java Platform → JDK 21`
3. Clic derecho en el proyecto → `Clean and Build`

### 5. Compilar y ejecutar

```bash
mvn clean install
```

Luego ejecuta la clase principal:

```
com.jcaa.usersmanagement.Main
```

---

## Menú principal

```
==========================================
     Users & Authors Management System
==========================================
    [1]  List all users
    [2]  Find user by ID
    [3]  Create user
    [4]  Update user
    [5]  Delete user
    [6]  Login
    [7]  Create Author
    [8]  Update Author
    [9]  Find Author By ID
    [10] List All Authors
    [11] Delete Author
    [12] Find Author By Email          ← Unidad IV
    [13] Find Authors By Name          ← Unidad IV
    [14] Find Authors By Work Center   ← Unidad IV
    [15] Find Authors By Country       ← Unidad IV
    [0]  Exit
==========================================
```

> Las opciones 12–15 son los 5 casos de uso de consulta implementados en la **Unidad IV**.

---

##  Ejecutar pruebas

```bash
mvn test
```

Resultado esperado: **193 tests, 0 failures**.

---

##  Arquitectura del proyecto

```
src/main/java/com/jcaa/usersmanagement/
├── application/
│   ├── port/
│   │   ├── in/          # Casos de uso (interfaces de entrada)
│   │   └── out/         # Puertos de salida (interfaces de persistencia)
│   └── service/         # Implementación de los casos de uso
│       └── dto/         # Commands y Queries
├── domain/
│   ├── model/           # Entidades del dominio
│   ├── valueobject/     # Value Objects
│   ├── exception/       # Excepciones del dominio
│   └── enums/           # Enumeraciones
└── infrastructure/
    ├── adapter/
    │   ├── email/       # Adaptador de correo (JavaMail)
    │   └── persistence/ # Repositorios MySQL (JDBC)
    ├── config/          # DependencyContainer, AppProperties
    └── entrypoint/
        └── desktop/
            ├── cli/     # Handlers y menú de consola
            ├── controller/
            ├── dto/
            └── mapper/
```

---

## Credenciales de prueba

| Campo | Valor |
|---|---|
| Email | `admin@example.com` |
| Password | `Admin1234!` |

---

## ⚠️ Notas importantes

- El **SMTP está deshabilitado** por defecto. Las notificaciones por correo están comentadas para evitar errores en entornos sin servidor SMTP configurado.
- El campo **country** no existe como columna separada. La búsqueda por país (`[15]`) filtra dentro del campo `work_center` usando `LIKE`. Al crear un autor incluye el país en el centro de trabajo, por ejemplo: `Universidad de Cartagena, Colombia`.
- El archivo `application.properties` en `src/test/resources` contiene valores ficticios para las pruebas unitarias y **no debe modificarse**.
