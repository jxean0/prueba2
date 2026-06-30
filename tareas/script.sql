CREATE DATABASE IF NOT EXISTS db_tareas;
USE db_tareas;

CREATE TABLE IF NOT EXISTS tareas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    prioridad VARCHAR(20) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    fecha_actualizacion DATETIME NOT NULL
);


INSERT INTO tareas (titulo, descripcion, estado, prioridad, fecha_creacion, fecha_actualizacion) VALUES
('Preparar sustentación', 'Repasar la explicación línea por línea del proyecto', 'PENDIENTE', 'ALTA', NOW(), NOW()),
('Configurar conexión MySQL', 'Verificar usuario root y base de datos db_tareas', 'COMPLETADA', 'URGENTE', NOW(), NOW()),
('Documentar endpoints', 'Escribir ejemplos de request/response para Postman', 'EN_PROGRESO', 'MEDIA', NOW(), NOW()),
('Revisar reglas de negocio', 'Validar transiciones de estado CANCELADA y COMPLETADA', 'PENDIENTE', 'ALTA', NOW(), NOW()),
('Limpiar tareas duplicadas', 'Tarea de prueba cancelada para verificar regla de bloqueo', 'CANCELADA', 'BAJA', NOW(), NOW());
