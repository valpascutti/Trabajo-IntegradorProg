# Scripts de Base de Datos
Esta carpeta contiene los scripts SQL necesarios para configurar la base de datos del proyecto.

## Archivos incluidos
# `schema.sql`
- Crea la base de datos `gestion_productos_tpi`
- Define las tablas `codigo_barras` y `producto`
- Establece las relaciones y restricciones
- Incluye índices para optimización

# `data.sql`
- Inserta datos de prueba en las tablas
- Incluye ejemplos de productos con y sin códigos de barras
- Datos listos para probar todas las funcionalidades del CRUD

# Cómo ejecutar

# Opción 1: MySQL Workbench
1. Abrir MySQL Workbench
2. Conectarse a tu servidor MySQL
3. Abrir y ejecutar `schema.sql`
4. Abrir y ejecutar `data.sql`

# Opción 2: Línea de comandos
```bash
# Ejecutar schema
mysql -u root -p < database/schema.sql

# Ejecutar data
mysql -u root -p < database/data.sql
```

# Opción 3: Desde MySQL CLI
```sql
SOURCE /ruta/completa/al/proyecto/database/schema.sql;
SOURCE /ruta/completa/al/proyecto/database/data.sql;
```

## Verificación
Después de ejecutar los scripts, deberías ver:
- Base de datos `gestion_productos_tpi` creada
- 2 tablas: `codigo_barras` y `producto`
- 5 códigos de barras de prueba
- 7 productos de prueba (5 con código de barras, 2 sin código)