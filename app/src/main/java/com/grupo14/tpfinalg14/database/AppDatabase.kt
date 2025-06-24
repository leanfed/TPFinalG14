package com.grupo14.tpfinalg14.database

// Importaciones necesarias para contexto y Room
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.grupo14.tpfinalg14.models.Usuario

// Anotación que define a esta clase como base de datos de Room
// Se indica que contiene la entidad Usuario y está en la versión 1
@Database(entities = [Usuario::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Método abstracto que expone el DAO para la entidad Usuario
    abstract fun usuarioDao(): UsuarioDao

    // Objeto companion que implementa patrón Singleton para instanciar una sola base
    companion object {

        // Instancia única de la base de datos, marcada como @Volatile para garantizar visibilidad en hilos múltiples
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Método público que devuelve una instancia existente o construye una nueva si no hay
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Construcción de la base de datos con Room
                val instance = Room.databaseBuilder(
                    context.applicationContext,         // Contexto de la aplicación para evitar fugas de memoria
                    AppDatabase::class.java,            // Clase de la base de datos
                    "educat_database"                   // Nombre físico del archivo de base de datos en disco
                ).build()

                INSTANCE = instance  // Guarda la instancia recién creada
                instance              // Devuelve la instancia
            }
        }
    }
}