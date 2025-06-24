package com.grupo14.tpfinalg14.ui.registro

// Importaciones necesarias para manejo de vistas, ciclo de vida, base de datos y corrutinas
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.grupo14.tpfinalg14.R
import com.grupo14.tpfinalg14.database.AppDatabase
import com.grupo14.tpfinalg14.models.Usuario
import kotlinx.coroutines.launch

// Actividad de registro de nuevos usuarios. Permite ingresar datos y almacenarlos localmente en la base de datos.
class RegisterActivity : AppCompatActivity() {

    // Vistas de entrada de datos
    private lateinit var editNombre: EditText       // Campo para ingresar el nombre
    private lateinit var editApellido: EditText     // Campo para ingresar el apellido
    private lateinit var editDocumento: EditText    // Campo para ingresar el número de documento
    private lateinit var editEmail: EditText        // Campo para ingresar el correo electrónico
    private lateinit var editContrasena: EditText   // Campo para ingresar la contraseña
    private lateinit var btnRegistrarse: Button     // Botón para confirmar el registro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Asocia esta actividad con su layout XML (activity_register.xml)
        setContentView(R.layout.activity_register)

        // Vincula cada vista del layout a sus variables correspondientes
        editNombre = findViewById(R.id.etNombre)
        editApellido = findViewById(R.id.etApellido)
        editDocumento = findViewById(R.id.etDoc)
        editEmail = findViewById(R.id.etEmail)
        editContrasena = findViewById(R.id.etPassword)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)

        // Accede al DAO de usuarios a través de la instancia de la base de datos
        val usuarioDao = AppDatabase.Companion.getDatabase(this).usuarioDao()

        // Define el comportamiento cuando el usuario hace clic en el botón "Registrarse"
        btnRegistrarse.setOnClickListener {
            // Extrae los textos ingresados por el usuario y remueve espacios innecesarios
            val nombre = editNombre.text.toString().trim()
            val apellido = editApellido.text.toString().trim()
            val documento = editDocumento.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val contrasena = editContrasena.text.toString().trim()

            // Verifica que todos los campos obligatorios estén completos
            if (nombre.isNotEmpty() && apellido.isNotEmpty() && documento.isNotEmpty() &&
                email.isNotEmpty() && contrasena.isNotEmpty()
            ) {
                // Crea un nuevo objeto Usuario con los datos ingresados
                val usuario = Usuario(
                    nombre = nombre,
                    apellido = apellido,
                    documento = documento,
                    email = email,
                    contrasena = contrasena
                )

                // Lanza una corrutina en el scope del ciclo de vida de la actividad para insertar el usuario
                lifecycleScope.launch {
                    usuarioDao.registrarUsuario(usuario) // Inserta el nuevo usuario en la base de datos

                    // Informa al usuario en la UI principal que el registro fue exitoso y cierra esta pantalla
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        finish() // Finaliza la actividad y regresa al Login
                    }
                }

            } else {
                // Si algún campo está vacío, muestra una advertencia al usuario
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}