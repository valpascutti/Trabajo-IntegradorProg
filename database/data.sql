-- DATOS DE PRUEBA - GESTIÓN DE PRODUCTOS

use gestion_productos_tpi;

-- INSERTAR CÓDIGOS DE BARRAS
INSERT INTO codigo_barras (valor, tipo, fecha_asignacion, observaciones, eliminado) VALUES 
('7895551112223', 'EAN13', '2024-01-15', 'Código asignado a producto lácteo - Lacteon', FALSE),
('7895551112230', 'EAN13', '2024-01-16', 'Código asignado a producto de limpieza - Higienop', FALSE),
('7896662223334', 'EAN13', '2024-01-17', 'Código asignado a snack dulce - Dulcimix', FALSE),
('7897773334445', 'EAN13', '2024-01-18', 'Código asignado a bebida - Coc', FALSE),
('7898884445556', 'EAN13', '2024-01-19', 'Código asignado a higiene personal - Lavandine', FALSE);


-- INSERTAR PRODUCTOS
INSERT INTO producto (nombre, marca, categoria, precio, peso, id_codigo_barras, eliminado) VALUES 
('Leche Entera 1L', 'Lacteon', 'Lácteos', 120.00, 1.000, 1, FALSE),
('Limpiador Multiuso 750ml', 'Higienop', 'Limpieza', 310.50, 0.750, 2, FALSE),
('Galletas Rellenas 150g', 'Dulcimix', 'Snacks', 150.00, 0.150, 3, FALSE),
('Gaseosa Sabor Cola 500ml', 'Coc', 'Bebidas', 130.00, 0.500, 4, FALSE),
('Shampoo Herbal 400ml', 'Lavandine', 'Higiene Personal', 260.00, 0.400, 5, FALSE);


-- PRODUCTOS SIN CÓDIGO DE BARRAS (para probar relaciones opcionales)
INSERT INTO producto (nombre, marca, categoria, precio, peso, id_codigo_barras, eliminado) VALUES 
('Producto Genérico A', 'Sin Marca', 'Varios', 50.00, 0.200, NULL, FALSE),
('Producto Genérico B', 'Sin Marca', 'Varios', 75.50, 0.300, NULL, FALSE);

-- VERIFICACIÓN DE DATOS INSERTADOS
SELECT 'Datos de prueba insertados exitosamente' AS status;

SELECT COUNT(*) AS total_codigos_barras FROM codigo_barras WHERE eliminado = FALSE;
SELECT COUNT(*) AS total_productos FROM producto WHERE eliminado = FALSE;

-- Mostrar productos con sus códigos de barras
SELECT 
    p.nombre AS producto,
    p.marca,
    p.categoria,
    p.precio,
    cb.valor AS codigo_barras,
    cb.tipo AS tipo_codigo
FROM producto p
LEFT JOIN codigo_barras cb ON p.id_codigo_barras = cb.id
WHERE p.eliminado = FALSE
ORDER BY p.categoria, p.nombre;