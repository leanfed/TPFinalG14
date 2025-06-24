package com.grupo14.tpfinalg14.database

// Importaciones necesarias de Room para definir DAOs y anotaciones SQL
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grupo14.tpfinalg14.models.Usuario

// Anotación que indica que esta interfaz es un DAO (Data Access Object)
// Representa una capa de abstracción sobre la base de datos para la entidad Usuario
@Dao
interface UsuarioDao {

    // Método suspendido que inserta un nuevo usuario en la base de datos
    // Si el email (clave primaria) ya existe, lo reemplaza (estrategia REPLACE)
    // Devuelve el ID de la fila insertada (email en este caso)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registrarUsuario(usuario: Usuario): Long

    // Método suspendido que realiza una consulta SELECT con cláusula WHERE
    // Busca un usuario cuyo email y contraseña coincidan con los ingresados
    // Si hay coincidencia, devuelve el objeto Usuario; si no, devuelve null
    @Query("SELECT * FROM usuarios WHERE email = :email AND contrasena = :contrasena")
    suspend fun login(email: String, contrasena: String): Usuario?
}