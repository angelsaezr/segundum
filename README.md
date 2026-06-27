# Segundum — Plataforma de compraventa de segunda mano

Aplicación web de compraventa de productos de segunda mano desarrollada con Java EE, JSF 2.3 y PrimeFaces 15, persistencia JPA con EclipseLink sobre MySQL, y CDI para inyección de dependencias. Permite a usuarios registrados publicar, buscar y gestionar sus productos.

---

## Características principales

- **Autenticación y sesión**: login/logout con filtro de autenticación (`AuthFilter`) que protege todas las rutas bajo `/segundum/*`.
- **Gestión de productos**: creación, edición (precio y descripción) y visualización de productos con lugar de recogida georreferenciado.
- **Búsqueda avanzada**: filtrado por texto, precio máximo, estado del producto y categoría (con expansión dinámica de jerarquía).
- **Categorías jerárquicas**: carga desde XML via JAXB, con soporte de árbol interactivo en UI.
- **Resumen mensual**: listado de productos publicados por un vendedor en un mes concreto, ordenado por visualizaciones.
- **Contador de visualizaciones**: se incrementa automáticamente cada vez que se accede al detalle de un producto.

---

## Tecnologías

| Capa | Tecnología |
|---|---|
| Frontend | JSF 2.3 + PrimeFaces 15 |
| Backend | Java 11, CDI (Weld) |
| Persistencia | JPA 2.2 (EclipseLink) + MySQL 8 |
| Servidor | Jetty 10 (via plugin Maven) |
| Serialización | JAXB 2.3 |
| Build | Maven |

---

## Estructura del proyecto

```
segundumSaezRosique/
├── src/main/java/segundum/
│   ├── modelo/          # Entidades JPA: Usuario, Producto, Categoria, LugarDeRecogida, EstadoProducto
│   ├── repositorio/     # Interfaces y implementaciones JPA de repositorios
│   ├── servicio/        # Lógica de negocio: IServicioUsuarios, IServicioProductos, IServicioCategorias
│   ├── especificacion/  # Patrón Especificación genérico
│   ├── utils/           # JPAUtil, EntityManagerHelper, PropertiesReader
│   └── web/             # Managed Beans JSF: Login, CrearProducto, MisProductos, BuscarProducto, etc.
├── src/main/webapp/
│   ├── WEB-INF/
│   │   ├── template/    # Plantilla Facelets (template.xhtml, footer.xhtml)
│   │   ├── beans.xml    # Configuración CDI
│   │   ├── faces-config.xml
│   │   └── web.xml
│   ├── index.xhtml
│   └── segundum/        # Páginas: login, crearProducto, misProductos, buscarProducto, verProducto, editarProducto
└── src/main/resources/
    ├── META-INF/persistence.xml
    ├── servicios.properties
    ├── repositorios.properties
    └── categoriasXML/   # Ficheros XML de jerarquía de categorías
```

---

## Configuración y arranque

### Requisitos previos

- Java 11+
- Maven 3.6+
- MySQL 8 en `localhost:3306`

### Base de datos

Crea la base de datos y el usuario:

```sql
CREATE DATABASE segundum CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Ajusta las credenciales en `persistence.xml` si es necesario (por defecto `root` / `practicas`).

Para que JPA genere las tablas automáticamente, cambia la propiedad en `persistence.xml`:

```xml
<property name="eclipselink.ddl-generation" value="create-or-extend-tables" />
```

### Arrancar la aplicación

```bash
mvn jetty:run
```

La aplicación estará disponible en `http://localhost:8080`.

### Cargar datos de prueba

Ejecuta la clase `segundum.servicio.test.Programa` como aplicación Java para insertar usuarios, categorías y productos de ejemplo.

---

## Patrones de diseño utilizados

- **Repository**: acceso a datos desacoplado de la lógica de negocio mediante interfaces (`Repositorio<T, K>`).
- **Factory**: `FactoriaRepositorios` y `FactoriaServicios` cargan implementaciones desde ficheros `.properties`, permitiendo sustituirlas sin tocar el código.
- **Specification**: clase genérica `Especificacion<T>` basada en `Predicate` para componer criterios de filtrado.
- **DTO**: `ProductoResumenMensual` como objeto de transferencia para el resumen mensual del vendedor.
- **Template Method**: `RepositorioJPA<T>` implementa las operaciones CRUD genéricas; las subclases solo especifican la clase de entidad.

---

## Autores

Ángel Sáez Rosique y Francisco Javier Ramírez López — AADD 2025/2026
