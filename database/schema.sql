-- Trabajo Práctico Integrador - Programación II
-- ESQUEMA DE BASE DE DATOS - GESTIÓN DE PRODUCTOS

CREATE DATABASE gestion_productos_tpi;
use gestion_productos_tpi;

-- TABLA: codigo_barras
CREATE TABLE codigo_barras (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  eliminado BOOLEAN,
  tipo ENUM('EAN13', 'EAN8', 'UPC') NOT NULL,
  valor VARCHAR(20) NOT NULL UNIQUE,
  fecha_asignacion DATE,
  observaciones VARCHAR(255)
);

-- TABLA: producto
CREATE TABLE producto (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  eliminado BOOLEAN,
  nombre VARCHAR(120) NOT NULL,
  marca VARCHAR(80),
  categoria VARCHAR(80),
  precio DECIMAL(10,2) NOT NULL,
  peso DECIMAL(10,3),
  id_codigo_barras BIGINT UNIQUE,
  FOREIGN KEY (id_codigo_barras) REFERENCES codigo_barras(id)
);

-- VERIFICACIÓN DE TABLAS CREADAS
SHOW TABLES;

SELECT 'Schema creado exitosamente' AS status;