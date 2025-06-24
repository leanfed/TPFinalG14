// Paquete donde se agrupa la lógica de acceso a datos simulados
package com.grupo14.tpfinalg14.repository

// Importación del modelo de datos que representa un registro de asistencia
import com.grupo14.tpfinalg14.models.Presentismo

// Objeto singleton que actúa como repositorio estático de registros de presentismo
object PresentismoRepository {

    // Lista fija de registros de asistencia para distintas materias y fechas
    val listaPresentismo = listOf(
        // ADMINISTRACIÓN DE BASES DE DATOS
        Presentismo("01/06/2025", "Administración de bases de datos", "Asistido"),
        Presentismo("02/06/2025", "Administración de bases de datos", "Falta"),
        Presentismo("03/06/2025", "Administración de bases de datos", "Asistido"),
        Presentismo("04/06/2025", "Administración de bases de datos", "Falta"),
        Presentismo("05/06/2025", "Administración de bases de datos", "Asistido"),
        Presentismo("06/06/2025", "Administración de bases de datos", "Falta"),

        // TÉCNICAS DE PROGRAMACIÓN
        Presentismo("01/06/2025", "Técnicas de programación", "Falta"),
        Presentismo("02/06/2025", "Técnicas de programación", "Asistido"),
        Presentismo("03/06/2025", "Técnicas de programación", "Asistido"),
        Presentismo("04/06/2025", "Técnicas de programación", "Falta"),
        Presentismo("05/06/2025", "Técnicas de programación", "Asistido"),
        Presentismo("06/06/2025", "Técnicas de programación", "Falta"),

        // LÓGICA COMPUTACIONAL
        Presentismo("01/06/2025", "Lógica computacional", "Asistido"),
        Presentismo("02/06/2025", "Lógica computacional", "Asistido"),
        Presentismo("03/06/2025", "Lógica computacional", "Falta"),
        Presentismo("04/06/2025", "Lógica computacional", "Asistido"),
        Presentismo("05/06/2025", "Lógica computacional", "Falta"),
        Presentismo("06/06/2025", "Lógica computacional", "Asistido"),

        // METODOLOGÍA DE PRUEBAS DE SISTEMAS
        Presentismo("01/06/2025", "Metodología de pruebas de sistemas", "Falta"),
        Presentismo("02/06/2025", "Metodología de pruebas de sistemas", "Falta"),
        Presentismo("03/06/2025", "Metodología de pruebas de sistemas", "Asistido"),
        Presentismo("04/06/2025", "Metodología de pruebas de sistemas", "Asistido"),
        Presentismo("05/06/2025", "Metodología de pruebas de sistemas", "Asistido"),
        Presentismo("06/06/2025", "Metodología de pruebas de sistemas", "Falta")
    )

    // 🔹 Devuelve un registro por materia con la última fecha de asistencia disponible
    fun getUltimaFechaPorMateria(): List<Presentismo> {
        return listaPresentismo
            .groupBy { it.materia } // Agrupa los registros por materia
            .mapValues { (_, registros) ->
                registros.maxByOrNull { it.fecha } // Busca el de fecha más reciente
            }
            .values
            .filterNotNull() // Elimina posibles nulos en caso de listas vacías
    }

    // 🔹 Filtra los registros de asistencia que pertenecen a una materia específica
    fun getPresentismoPorMateria(nombreMateria: String): List<Presentismo> {
        return listaPresentismo.filter { it.materia == nombreMateria }
    }

    // 🔹 Calcula el porcentaje de asistencia sobre el total de registros de una materia
    fun calcularPorcentajePresentismo(nombreMateria: String): Int {
        val registros = getPresentismoPorMateria(nombreMateria)         // Registros filtrados por materia
        val asistencias = registros.count { it.estado == "Asistido" }  // Cuenta cuántas veces fue "Asistido"
        return (asistencias * 100) / registros.size                    // Devuelve el porcentaje total
    }
}