# Programación en Móviles - GymTracker Pro

## Información del Proyecto
*   **Curso:** Programación en Móviles
*   **Integrantes:**
    *   Bellido Rony
    *   Rojas Mauricio
*   **Nombre del Proyecto:** GymTracker Pro
*   **Estado:** Funcional (Correcciones de configuración aplicadas)

## Descripción
GymTracker Pro es una aplicación Android diseñada para el seguimiento de rutinas de ejercicios. Permite a los usuarios registrarse, iniciar sesión y gestionar sus entrenamientos diarios, calculando métricas como el volumen total de carga.

## Arquitectura y Tecnologías
La aplicación sigue los patrones modernos de desarrollo de Android recomendados por Google:

*   **Lenguaje:** Kotlin
*   **UI Framework:** Jetpack Compose (Material 3)
*   **Arquitectura:** MVVM (Model-View-ViewModel) con Repositorio.
*   **Persistencia de Datos:** Room Database (SQLite local).
*   **Networking:** Retrofit 2 + Gson para consumo de APIs externas (Exercise API).
*   **Navegación:** Jetpack Compose Navigation.
*   **Gestión de Dependencias:** Gradle con Kotlin DSL y KSP.

## Componentes del Proyecto

### 1. Capa de Datos (Data)
*   **Local (`data/local`):** 
    *   `Usuario` y `Rutina`: Entidades de la base de datos.
    *   `UsuarioDao` y `RutinaDao`: Interfaces para operaciones CRUD.
    *   `AppDatabase`: Punto de entrada de la base de datos Room.
*   **Remote (`data/remote`):** 
    *   `ExerciseApiService`: Definición de endpoints para obtener datos de ejercicios externos.
    *   `RetrofitClient`: Configuración del cliente HTTP.
*   **Repository (`data/repository`):**
    *   `GymRepository`: Clase que actúa como fuente única de verdad, mediando entre datos locales y remotos.

### 2. Capa de Interfaz de Usuario (UI)
*   **Screens (`ui/screens`):**
    *   `AuthScreens`: Pantallas de Login y Registro.
    *   `MenuPrincipalScreen`: Panel central con acceso rápido y navegación lateral.
    *   `RutinaScreens`: Gestión de rutinas (Agregar, Listar, Editar/Eliminar).
    *   `PerfilUsuarioScreen`: Estadísticas del usuario (Volumen total y conteo de rutinas).
*   **ViewModel (`ui/viewmodel`):**
    *   `GymViewModel`: Gestiona el estado de la UI y la lógica de negocio, comunicándose con el repositorio.
*   **Navigation (`ui/navigation`):**
    *   `Routes`: Definición de constantes y parámetros de navegación.
    *   `GymTrackerNavHost`: Grafo de navegación de la aplicación.

## Características Implementadas
1.  **Autenticación:** Registro y Login de usuarios con validación de credenciales local.
2.  **Gestión de Rutinas:** Operaciones completas (Crear, Leer, Actualizar, Borrar).
3.  **Estadísticas en Tiempo Real:** Cálculo automático del volumen de entrenamiento (Series x Repeticiones x Peso) usando consultas reactivas de Room (`Flow`).
4.  **Diseño Adaptable:** Uso de `Scaffold`, `TopAppBar` y `ModalNavigationDrawer` para una experiencia de usuario fluida.

## Notas de Configuración
El proyecto ha sido actualizado para cumplir con:
*   **Android Gradle Plugin (AGP):** 8.5.2
*   **Gradle:** 8.10.2
*   **Target SDK:** 34
*   **Compose Compiler:** 2.0.21 (Kotlin 2.0)
