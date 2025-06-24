// Este archivo define el modelo de datos para representar a un usuario registrado en la app
package com.grupo14.tpfinalg14.models

// Importaciones necesarias de Room para persistencia local
import androidx.room.Entity
import androidx.room.PrimaryKey

// Anotación que marca esta clase como una entidad de base de datos de Room
// El nombre de la tabla será "usuarios" en lugar del nombre por defecto de la clase
@Entity(tableName = "usuarios")
data class Usuario(
    // Clave primaria de la tabla, generada automáticamente por Room
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // Nombre del usuario (por ejemplo: "María")
    val nombre: String,

    // Apellido del usuario (por ejemplo: "Gómez")
    val apellido: String,

    // Documento nacional o identificador (por ejemplo: "40111222")
    val documento: String,

    // Correo electrónico del usuario (clave lógica usada para login)
    val email: String,

    // Contraseña para acceder (se guarda en texto plano para pruebas; en producción debería encriptarse)
    val contrasena: String
)