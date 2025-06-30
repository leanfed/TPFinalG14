Educat App

Educat es una aplicación Android académica que organiza en un solo lugar los módulos clave para estudiantes: calendario, vencimientos, cronograma, calificaciones, presentismo y perfil. Su arquitectura modular basada en MVVM (Model–View–View-Model) garantiza escalabilidad, mantenibilidad y separación clara de responsabilidades.

Funcionalidades principales
- Login / Registro local con validación mediante Room Database
- Pantalla de Inicio (Splash) animada y personalizada
- Perfil de usuario con datos persistidos localmente
- Presentismo, vencimientos académicos y calificaciones gestionados de forma dinámica
- Cronograma semanal y calendario visual
- Menú contextual y barra inferior reutilizable en todos los módulos

Arquitectura y tecnologías
- Lenguaje: Kotlin
- Patrón: MVVM
- UI: Fragments, RecyclerView, ConstraintLayout, PopupMenu, BottomBar
- Persistencia: Room Database (UsuarioDao, UsuarioEntity, EducatDatabase)
- Navegación: Manual con FragmentManager (replace, addToBackStack)
- Modularidad: Navegación desacoplada y reutilización de adaptadores y layouts

Próximos pasos (escalabilidad)
- Reemplazar repositorios mockeados por endpoints reales usando Retrofit
- Persistencia de sesión y autenticación externa (Firebase/Auth0)
- Migración progresiva a Navigation Component si se requiere backstack avanzado
- Notificaciones y tareas programadas para vencimientos

Estado actual
Educat App se encuentra en fase de desarrollo académico avanzado. Todos los módulos son funcionales con datos locales; el diseño y la arquitectura están listos para escalar a integración remota.



