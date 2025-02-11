package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Clase para gestionar la base de datos
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "app_db"
        private const val DATABASE_VERSION = 2 // Incrementa la versión si haces cambios en la base de datos

        // Nombre de las tablas
        const val TABLE_SERVICIO = "servicios"
        const val TABLE_SUSCRIPCION = "suscripciones"

        // Nombres de las columnas para la tabla "servicios"
        const val COLUMN_SERVICIO_ID = "id"
        const val COLUMN_SERVICIO_NOMBRE = "nombre"
        const val COLUMN_SERVICIO_PLATAFORMA = "plataforma"
        const val COLUMN_SERVICIO_DURACION = "duracion"

        // Nombres de las columnas para la tabla "suscripciones"
        const val COLUMN_SUSCRIPCION_ID = "id"
        const val COLUMN_SUSCRIPCION_USUARIO_ID = "usuario_id"
        const val COLUMN_SUSCRIPCION_FECHA_INICIO = "fecha_inicio"
        const val COLUMN_SUSCRIPCION_METODO_PAGO = "metodo_pago"
        const val COLUMN_SUSCRIPCION_SERVICIO_ID = "servicio_id" // Añadir columna para asociar la suscripción con el servicio
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear la tabla de servicios
        val createServiciosTable = """
            CREATE TABLE $TABLE_SERVICIO (
                $COLUMN_SERVICIO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_SERVICIO_NOMBRE TEXT,
                $COLUMN_SERVICIO_PLATAFORMA TEXT,
                $COLUMN_SERVICIO_DURACION TEXT
            )
        """
        db.execSQL(createServiciosTable)

        // Crear la tabla de suscripciones con la relación con el servicio
        val createSuscripcionesTable = """
            CREATE TABLE $TABLE_SUSCRIPCION (
                $COLUMN_SUSCRIPCION_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_SUSCRIPCION_USUARIO_ID INTEGER,
                $COLUMN_SUSCRIPCION_FECHA_INICIO TEXT,
                $COLUMN_SUSCRIPCION_METODO_PAGO TEXT,
                $COLUMN_SUSCRIPCION_SERVICIO_ID INTEGER,  -- Relación con servicios
                FOREIGN KEY($COLUMN_SUSCRIPCION_SERVICIO_ID) REFERENCES $TABLE_SERVICIO($COLUMN_SERVICIO_ID)
            )
        """
        db.execSQL(createSuscripcionesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Eliminar tablas antiguas y recrearlas
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SERVICIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUSCRIPCION")
        onCreate(db) // Volver a crear las tablas con la nueva versión
    }

    // Método para insertar un servicio
    fun insertServicio(servicio: Servicio): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SERVICIO_NOMBRE, servicio.nombre)
            put(COLUMN_SERVICIO_PLATAFORMA, servicio.plataforma)
            put(COLUMN_SERVICIO_DURACION, servicio.duracion)
        }
        return db.insert(TABLE_SERVICIO, null, values)
    }

    fun insertSuscripcion(suscripcion: Suscripcion): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SUSCRIPCION_USUARIO_ID, suscripcion.usuarioId)
            put(COLUMN_SUSCRIPCION_FECHA_INICIO, suscripcion.fechaInicio)
            put(COLUMN_SUSCRIPCION_METODO_PAGO, suscripcion.metodoPago)
            put(COLUMN_SUSCRIPCION_SERVICIO_ID, suscripcion.servicioId) // Asegúrate de que servicioId esté presente
        }
        return db.insert(TABLE_SUSCRIPCION, null, values)
    }




    // Método para obtener todos los servicios
    fun getAllServicios(): List<Servicio> {
        val servicios = mutableListOf<Servicio>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_SERVICIO, null, null, null, null, null, null
        )

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_SERVICIO_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_SERVICIO_NOMBRE))
                val plataforma = cursor.getString(cursor.getColumnIndex(COLUMN_SERVICIO_PLATAFORMA))
                val duracion = cursor.getString(cursor.getColumnIndex(COLUMN_SERVICIO_DURACION))

                servicios.add(Servicio(id, nombre, plataforma, duracion))
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return servicios
    }

    // Método para obtener las suscripciones de un servicio
    fun getSuscripcionesByServicio(servicioId: Long): List<Suscripcion> {
        val suscripciones = mutableListOf<Suscripcion>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_SUSCRIPCION, null, "$COLUMN_SUSCRIPCION_USUARIO_ID = ?",
            arrayOf(servicioId.toString()), null, null, null
        )

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_SUSCRIPCION_ID))
                val usuarioId = cursor.getLong(cursor.getColumnIndex(COLUMN_SUSCRIPCION_USUARIO_ID))
                val fechaInicio = cursor.getString(cursor.getColumnIndex(COLUMN_SUSCRIPCION_FECHA_INICIO))
                val metodoPago = cursor.getString(cursor.getColumnIndex(COLUMN_SUSCRIPCION_METODO_PAGO))

                suscripciones.add(Suscripcion(id, usuarioId, fechaInicio, metodoPago, servicioId))
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return suscripciones
    }

    // Método para actualizar un servicio
    fun updateServicio(servicio: Servicio): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SERVICIO_NOMBRE, servicio.nombre)
            put(COLUMN_SERVICIO_PLATAFORMA, servicio.plataforma)
            put(COLUMN_SERVICIO_DURACION, servicio.duracion)
        }

        return db.update(
            TABLE_SERVICIO, values, "$COLUMN_SERVICIO_ID = ?",
            arrayOf(servicio.id.toString())
        )
    }

    // Método para eliminar un servicio
    fun deleteServicio(servicioId: Long): Int {
        val db = writableDatabase
        return db.delete(
            TABLE_SERVICIO, "$COLUMN_SERVICIO_ID = ?",
            arrayOf(servicioId.toString())
        )
    }

    // Método para actualizar una suscripción
    fun updateSuscripcion(suscripcion: Suscripcion): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SUSCRIPCION_USUARIO_ID, suscripcion.usuarioId)
            put(COLUMN_SUSCRIPCION_FECHA_INICIO, suscripcion.fechaInicio)
            put(COLUMN_SUSCRIPCION_METODO_PAGO, suscripcion.metodoPago)
        }

        return db.update(
            TABLE_SUSCRIPCION, values, "$COLUMN_SUSCRIPCION_ID = ?",
            arrayOf(suscripcion.id.toString())
        )
    }

    // Método para eliminar una suscripción
    fun deleteSuscripcion(suscripcionId: Long): Int {
        val db = writableDatabase
        return db.delete(
            TABLE_SUSCRIPCION, "$COLUMN_SUSCRIPCION_ID = ?",
            arrayOf(suscripcionId.toString())
        )
    }
    // Método para eliminar todos los servicios
    fun deleteAllServicios() {
        val db = writableDatabase
        db.delete(TABLE_SERVICIO, null, null)
    }

    // Método para eliminar todas las suscripciones
    fun deleteAllSuscripciones() {
        val db = writableDatabase
        db.delete(TABLE_SUSCRIPCION, null, null)
    }

}
