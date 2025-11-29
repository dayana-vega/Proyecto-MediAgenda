# MediAgenda - Sistema de Agendamiento Médico

Proyecto Full Stack para la gestión de citas médicas, desarrollado con Spring Boot y React.

## 👥 Integrantes del Grupo
* **Dayana Vega**
* **Encar Portillo**


## 🚀 Tecnologías
* **Backend:** Java 17, Spring Boot 3 (Web, JPA, Validation), MySQL.
* **Frontend:** React, TypeScript, Vite, Tailwind CSS, Shadcn/ui, Axios.
* **Herramientas:** Postman, Swagger, MySQL Workbench.

## ⚙️ Instrucciones de Instalación

### Backend
1.  Clonar el repositorio.
2.  Configurar `application.properties` con tu base de datos MySQL local.
3.  Ejecutar `MediagendaApplication.java`.
4.  Swagger disponible en: `http://localhost:8080/swagger-ui/index.html`

### Frontend
1.  Entrar a la carpeta `frontend-mediagenda`.
2.  Ejecutar `npm install`.
3.  Ejecutar `npm run dev`.
4.  Abrir `http://localhost:5173`.

## ✨ Funcionalidades Principales
1.  **Roles:** Sistema multi-rol (Admin, Médico, Paciente).
2.  **Agenda Dinámica:** Cálculo de bloques horarios disponibles en tiempo real.
3.  **Pagos:** Simulación de pago y cambio de estado de citas.
4.  **Seguridad:** Protección de rutas mediante `RoleGuard`.
