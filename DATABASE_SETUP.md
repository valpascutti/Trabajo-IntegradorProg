# INSTRUCCIONES PARA CONFIGURAR LA BASE DE DATOS

## 🔧 Setup inicial para cada desarrollador:

1. Copia el archivo `db.properties.template` y renómbralo a `db.properties`
2. Edita `db.properties` con tus credenciales locales
3. ¡Nunca hagas commit del archivo `db.properties`!

## 📋 Ejemplo de configuración:

### Para XAMPP (por defecto):
```
DB_URL=jdbc:mysql://localhost:3306/gestion_productos_tpi?serverTimezone=UTC
DB_USER=root
DB_PASSWORD=
```

### Para MySQL instalación normal:
```
DB_URL=jdbc:mysql://localhost:3306/gestion_productos_tpi?serverTimezone=UTC
DB_USER=root
DB_PASSWORD=tu_password_real
```

## ⚠️ IMPORTANTE:
- El archivo `db.properties` está en `.gitignore` - no se subirá al repo
- Cada desarrollador debe tener su propia copia local
- Si cambias la estructura de la BD, actualiza el template

## 🚀 Para nuevos integrantes:
```bash
cd src/config
cp db.properties.template db.properties
# Editar db.properties con tus datos
```