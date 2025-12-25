# Sistema de Gestión Académica 🎓

Aplicación de escritorio desarrollada en Java para la administración integral de una institución educativa. Permite gestionar alumnos, abonos, profesores, cursos, inscripciones y calificaciones.

---

## 🚀 Funcionalidades Clave

### 👥 Gestión de Alumnos y Profesores
- **CRUD Completo:** Alta, Baja (lógica/física), Modificación y Consulta de perfiles.
- **Validación de Datos:** Control de entradas para asegurar la integridad de la información (emails, nombres, etc.).

### 📚 Administración de Cursos
- Creación de cursos con asignación de cupos máximos, precios y profesores a cargo.
- Control de estado (cursos activos/inactivos).

### 📝 Inscripciones y Matriculación
- Sistema transaccional para inscribir alumnos a cursos verificando **disponibilidad de cupos**.
- Cálculo automático de ingresos y gestión de estados de inscripción.

### 📊 Reportes y Dashboard
- **Visualización de Datos:** Tablas interactivas para listar entidades.
- **Reportes Estadísticos:** Generación de métricas como "Total Recaudado por Curso", "Cupos Disponibles" y "Alumnos Inscriptos".
- **Búsqueda y Filtrado:** Herramientas para localizar información rápidamente.

---

## 🛠️ Tecnologías y Arquitectura

El proyecto sigue una **Arquitectura en Capas (Layered Architecture)** para asegurar la escalabilidad y el mantenimiento del código:

1.  **Capa de Presentación (GUI):**
    - Construida con **Java Swing**.
    - Uso intensivo de **GridBagLayout** para interfaces responsivas y profesionales.
    - Diseño modular con `JPanels` reutilizables y `PanelManager` para la navegación.

2.  **Capa de Servicio (Logic):**
    - Clases `Service` que contienen la lógica de negocio (validaciones, cálculos de cupos, reglas de inscripción).
    - Desacoplamiento entre la vista y la base de datos.

3.  **Capa de Persistencia (DAO):**
    - Conexión a Base de Datos (SQL) mediante **JDBC**.
    - Mapeo Objeto-Relacional manual para transformar `ResultSets` en objetos Java (`Alumno`, `Curso`).

---

## 🔧 Instalación y Ejecución

**Requisitos previos:**
- Java Development Kit (JDK) 17 o superior.
- Base de datos H2 / MySQL (según configuración).
- IntelliJ IDEA o Eclipse.

**Pasos:**
1.  Clonar el repositorio:
    ```bash
    git clone [https://github.com/agussderoo7/Sistema-Gestion-Alumnos-Java.git](https://github.com/agussderoo7/Sistema-Gestion-Alumnos-Java.git)
    ```
2.  Abrir el proyecto en su IDE de preferencia.
3.  Configurar las credenciales de la base de datos en `ConnectionFactory` o `application.properties`.
4.  Ejecutar la clase `Main` o `App`.

---

## 💡 Aprendizajes y Futuras Mejoras

Este proyecto me permitió consolidar conocimientos en:
- Manejo avanzado de **Layouts en Swing**.
- Gestión de excepciones y errores (Try-Catch, Validaciones de nulos).
- Lógica SQL.

**Próximos pasos (Roadmap):**
- [ ] Exportación de reportes a Excel (Apache POI).
- [ ] Implementación de Login y Roles de usuario.

---

**Autor:** Agustin De Roo

**Contacto:** agustinderoo05@gmail.com | www.linkedin.com/in/agustinderoo
