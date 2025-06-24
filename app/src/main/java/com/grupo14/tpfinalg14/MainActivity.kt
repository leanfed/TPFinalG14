package com.grupo14.tpfinalg14

// Importaciones necesarias para trabajar con actividades, fragmentos y navegación
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.grupo14.tpfinalg14.ui.*             // Importa todos los paquetes del módulo de interfaz
import com.grupo14.tpfinalg14.ui.login.LoginFragment

// MainActivity es el punto de entrada de la aplicación luego del Splash.
// Es una actividad contenedora de fragmentos: administra la carga y reemplazo de fragmentos
// en un espacio definido dentro del layout 'activity_main.xml'.
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Asocia esta actividad con su archivo de diseño: activity_main.xml
        // Este layout debe incluir un contenedor como <FrameLayout android:id="@+id/fragment_container">
        // en donde se mostrarán dinámicamente los fragmentos (Login, Home, Perfil, etc).
        setContentView(R.layout.activity_main)

        // Solo agregamos el fragmento inicial si es la primera vez que se crea la actividad.
        // Esto evita recrearlo al rotar la pantalla o al volver desde el back stack.
        if (savedInstanceState == null) {
            // Al iniciar, cargamos el LoginFragment como pantalla inicial
            // Este será reemplazado luego por otros fragmentos como HomeFragment, PerfilFragment, etc.
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment()) // Inserta LoginFragment en el contenedor
                .commit()
        }
    }
}