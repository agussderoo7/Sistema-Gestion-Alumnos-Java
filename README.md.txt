# Sistema de Gestión Académica 🎓

Aplicación de escritorio desarrollada en Java para la administración integral de una institución educativa. Permite gestionar alumnos, abonos, profesores, cursos, inscripciones y calificaciones.

## Características Principales

* Arquitectura MVC: Separación limpia entre Modelo (Entidades), Vista (Swing) y Controlador/Servicios.
* Gestión de Datos: CRUD completo para Alumnos, Profesores y Cursos.
* Reglas de Negocio Complejas:**
    * Validación de cupos y límites de inscripción.
    * Cálculo automático de promedios y aprobación.
    * Gestión de promociones por fecha y becas (Abonos).
* Persistencia: Conexión a base de datos SQL (H2) mediante JDBC y DAOs.
* Integridad Referencial: Base de datos con borrado en cascada para mantener la consistencia.

## Tecnologías Utilizadas

* Lenguaje: Java (JDK 17+)
* Interfaz Gráfica: Java Swing (JPanel, JTable, Layouts personalizados).
* Base de Datos: H2 Database (SQL).
* Patrones: DAO (Data Access Object), Service Layer.