# Sistema de Verificación Pico y Placa

Implementación full stack del sistema de restricción vehicular "Pico y Placa".

## Backend (Spring Boot)

### Pre-requisitos
- Java 17+
- Maven 3.8+
- SQL Server 2019+

### Configuración Base de Datos
```sql
CREATE DATABASE picoplaca;
CREATE LOGIN sa WITH PASSWORD = 'your_password';
USE picoplaca;
CREATE USER sa FOR LOGIN sa;
EXEC sp_addrolemember 'db_owner', 'sa';
