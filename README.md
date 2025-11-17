# Gestión de Productos - Trabajo Práctico Integrador

## Descripción del dominio
El sistema modela una relación unidireccional 1:1 entre Producto → CódigoBarras.
Cada producto puede tener un único código de barras.
El proyecto implementa un CRUD por consola utilizando Java, JDBC, DAOs y manejo transaccional.

---

## Requisitos del sistema
- **Java 17+**  
- **MySQL 8.0+**  
- **JDBC** con MySQL Connector/J
- **Patrón DAO** con soporte transaccional
- **Arquitectura en capas** (DAO, Service, Entities)

### Dependencias incluidas
- MySQL Connector/J 8.0+ (debe agregarse al classpath)

---

## Configuración de la base de datos

### 1. Ejecutar scripts SQL
El proyecto incluye scripts SQL listos para usar en la carpeta `database/`:

**Archivos disponibles:**
- `schema.sql` → Crea la base de datos y estructura de tablas
- `data.sql` → Inserta datos de prueba para testing
- `README.md` → Instrucciones detalladas para configuración

**Opción 1: MySQL Workbench**
1. Abrir MySQL Workbench y conectarse a tu servidor
2. Ejecutar `database/schema.sql` (crea BD y tablas)
3. Ejecutar `database/data.sql` (inserta 5 códigos + 7 productos de prueba)

**Opción 2: Línea de comandos**
```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/data.sql
```

### 2. Verificar instalación
```sql
USE gestion_productos_tpi;
SHOW TABLES;                        -- Debe mostrar: codigo_barras, producto
SELECT COUNT(*) FROM producto;      -- Debería mostrar: 7
SELECT COUNT(*) FROM codigo_barras; -- Debería mostrar: 5
```

---

## Cómo compilar y ejecutar

### 1. Clonar el repositorio
```bash
git clone https://github.com/valpascutti/Trabajo-IntegradorProg.git
cd Trabajo-IntegradorProg
```

### 2. Configurar credenciales de base de datos
**IMPORTANTE:** Cada desarrollador debe configurar sus credenciales localmente.

```bash
# Copiar el template y configurar credenciales personales
cp src/config/db.properties.template src/config/db.properties
```

**Editar `src/config/db.properties`** con tus datos:
```properties
DB_URL=jdbc:mysql://localhost:3306/gestion_productos_tpi?serverTimezone=UTC
DB_USER=root
DB_PASSWORD=tu_password_aqui
```

### 3. Descargar MySQL Connector/J
1. Descargar desde: [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
2. Crear carpeta `lib/` en la raíz del proyecto
3. Colocar el archivo `.jar` en `lib/`

### 4. Compilar el proyecto
```bash
# Compilar desde la raíz del proyecto (Windows)
javac -d bin -cp "lib/*;src" src/main/App.java src/main/AppMenu.java src/main/TestConexion.java src/dao/*.java src/entities/*.java src/service/*.java src/config/*.java src/test/*.java

# Para Linux/Mac usar : en lugar de ;
javac -d bin -cp "lib/*:src" src/main/App.java src/main/AppMenu.java src/main/TestConexion.java src/dao/*.java src/entities/*.java src/service/*.java src/config/*.java src/test/*.java
```

### 5. Ejecutar el programa
```bash
# Probar conexión a la BD primero
java -cp "bin;lib/*" main.TestConexion     # Windows
java -cp "bin:lib/*" main.TestConexion     # Linux/Mac

# Ejecutar aplicación principal
java -cp "bin;lib/*" main.App              # Windows
java -cp "bin:lib/*" main.App              # Linux/Mac

# Ejecutar ejemplo de transacciones
java -cp "bin;lib/*" test.EjemploTransacciones
```

### Credenciales de prueba
- **Base de datos:** `gestion_productos_tpi`
- **Usuario:** `root` (o tu usuario MySQL configurado)
- **Password:** El que configures en `db.properties`
- **Puerto:** 3306 (puerto por defecto de MySQL)

---

## Flujo de uso de la aplicación

### 1. **Iniciar aplicación**
```bash
java -cp "bin;lib/*" main.App
```

### 2. **Menú principal**
Al iniciar verás opciones para:
- Gestionar Productos
- Gestionar Códigos de Barras
- Salir

### 3. **Operaciones disponibles para cada entidad**
- Crear
- Listar
- Buscar por ID específico
- Buscar productos por nombre
- Actualizar 
- Eliminar

### 4. **Funcionalidades especiales**
- Asociar un Código de Barras a un Producto
- Operaciones transaccionales (commit/rollback)
- Validaciones e integridad referencial

### 5. **Ejemplo práctico - Crear producto con código**
- Crear un Código de Barras
- Crear un Producto y asociarle el código

---

## Enlace al video demostrativo: 
(LINK_AL_VIDEO)

---

## Equipo de desarrollo
- Valentina Pascutti
- Angela Ramos
- Nazareno Malpassi

**Materia:** Programación II  
**Institución:** Universidad Tecnológica Nacional (UTN)  
**Año:** 2025

