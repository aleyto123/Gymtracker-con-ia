# Programación en Móviles - GymTracker Pro 🚀

## 📋 Información del Proyecto
*   **Curso:** Programación en Móviles
*   **Integrantes:**
    *   👨‍💻 **Bellido Rony**
    *   👨‍💻 **Rojas Mauricio**
*   **Nombre del Proyecto:** GymTracker Pro (IA Enhanced Edition)
*   **Estado:** Versión 2.0 - Funcional con Integración de IA

---

## 📝 Descripción
GymTracker Pro es una solución integral para entusiastas del fitness que buscan no solo registrar sus entrenamientos, sino también optimizarlos mediante el análisis de datos. La aplicación permite gestionar rutinas de ejercicios, calcular volúmenes de carga y ahora integra capacidades de Inteligencia Artificial para llevar el entrenamiento al siguiente nivel.

---

## 🤖 Nuevas Implementaciones con IA
En esta fase del proyecto, hemos integrado capas de Inteligencia Artificial para mejorar la experiencia del usuario y la efectividad del entrenamiento:

1.  **Recomendaciones Inteligentes:** Algoritmos de IA que analizan el historial de volumen de carga para sugerir incrementos de peso progresivos o periodos de descarga automáticos.
2.  **Análisis de Rendimiento Predictivo:** Uso de modelos para predecir la fatiga muscular basada en la frecuencia y la intensidad de las rutinas registradas.
3.  **Asistente de Ejercicios:** Integración con APIs de procesamiento de lenguaje natural para buscar y categorizar ejercicios específicos según el grupo muscular, optimizando la búsqueda en nuestra base de datos remota.

---

## 🎨 Nueva Interfaz de Usuario (UI/UX)
La interfaz ha sido rediseñada desde cero utilizando **Jetpack Compose** para ofrecer una experiencia fluida y moderna:

*   **Material Design 3:** Implementación completa del sistema de diseño Material 3, incluyendo colores dinámicos y tipografía adaptable.
*   **Panel de Control (Dashboard):** Un menú principal intuitivo con acceso rápido a las estadísticas clave del usuario.
*   **Visualización de Datos:** Pantallas de perfil mejoradas con representaciones claras del volumen total y progreso acumulado.
*   **Navegación Intuitiva:** Uso de `ModalNavigationDrawer` y `Scaffold` para una navegación rápida entre el registro de rutinas y el análisis de perfil.
*   **Modo Oscuro/Claro:** Soporte nativo para temas que se adaptan a la configuración del sistema del usuario.

---

## 🛠️ Arquitectura y Tecnologías
La aplicación sigue los estándares de la industria para garantizar escalabilidad y mantenimiento:

*   **Lenguaje:** Kotlin 2.0 (Corrutinas y Flow para reactividad).
*   **Persistencia:** Room Database con consultas asíncronas para estadísticas en tiempo real.
*   **Networking:** Retrofit 2 para el consumo de la API de ejercicios externos.
*   **Inyección/Gestión de Datos:** Patrón Repositorio como fuente única de verdad.
*   **Build System:** Gradle con Kotlin DSL y KSP para una compilación optimizada.

---

## 🚀 Características Implementadas
1.  **Módulo de Autenticación:** Registro y Login con validación local segura.
2.  **Gestor de Rutinas Completo:** Operaciones CRUD para ejercicios individuales.
3.  **Cálculo de Volumen Real-Time:** Cálculo automático de (Peso x Reps x Series) que se actualiza al instante.
4.  **Integración Remota:** Búsqueda de ejercicios en tiempo real mediante API externa.

---

## ⚙️ Requisitos de Configuración
*   **Android SDK:** Target 34 (Android 14)
*   **Gradle Version:** 8.10.2
*   **AGP Version:** 8.5.2
*   **Minimum API:** 24 (Android 7.0)

---
*Este proyecto es parte del entregable final para el curso de Programación en Móviles.*
