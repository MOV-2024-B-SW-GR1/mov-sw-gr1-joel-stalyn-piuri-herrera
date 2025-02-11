package com.example.deber2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.deber2.models.Suscripcion

class SuscripcionDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun insertarSuscripcion(suscripcion: Suscripcion): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_SUSCRIPCION_NOMBRE, suscripcion.nombre)
            put(DatabaseHelper.COLUMN_SUSCRIPCION_DURACION, suscripcion.duracion)
            put(DatabaseHelper.COLUMN_SUSCRIPCION_COSTO, suscripcion.costo)
            put(DatabaseHelper.COLUMN_SUSCRIPCION_SERVICIO_ID, suscripcion.servicioId)
        }
        val id = db.insert(DatabaseHelper.TABLE_SUSCRIPCION, null, values)
        db.close()
        return id
    }

    fun obtenerSuscripcionesPorServicio(servicioId: Int): List<Suscripcion> {
        val suscripciones = mutableListOf<Suscripcion>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_SUSCRIPCION} WHERE ${DatabaseHelper.COLUMN_SUSCRIPCION_SERVICIO_ID} = ?",
            arrayOf(servicioId.toString())
        )

        while (cursor.moveToNext()) {
            val suscripcion = Suscripcion(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUSCRIPCION_ID)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUSCRIPCION_NOMBRE)),
                duracion = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUSCRIPCION_DURACION)),
                costo = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUSCRIPCION_COSTO)),
                servicioId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUSCRIPCION_SERVICIO_ID))
            )
            suscripciones.add(suscripcion)
        }
        cursor.close()
        db.close()
        return suscripciones
    }

    fun obtenerSuscripcionPorId(id: Int): Suscripcion? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_SUSCRIPCION} WHERE ${DatabaseHelper.COLUMN_SUSCRIPCION_ID} = ?",
            arrayOf(id.toString())
        )

        return if (cursor.moveToFirst()) {
            Suscripcion(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUSCRIPCION_ID)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUSCRIPCION_NOMBRE)),
                duracion = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUSCRIPCION_DURACION)),
                costo = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUSCRIPCION_COSTO)),
                servicioId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUSCRIPCION_SERVICIO_ID))
            )
        } else {
            null
        }.also {
            cursor.close()
            db.close()
        }
    }

    fun actualizarSuscripcion(id: Int, suscripcion: Suscripcion): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_SUSCRIPCION_NOMBRE, suscripcion.nombre)
            put(DatabaseHelper.COLUMN_SUSCRIPCION_DURACION, suscripcion.duracion)
            put(DatabaseHelper.COLUMN_SUSCRIPCION_COSTO, suscripcion.costo)
            put(DatabaseHelper.COLUMN_SUSCRIPCION_SERVICIO_ID, suscripcion.servicioId)
        }
        val result = db.update(
            DatabaseHelper.TABLE_SUSCRIPCION,
            values,
            "${DatabaseHelper.COLUMN_SUSCRIPCION_ID} = ?",
            arrayOf(id.toString())
        )
        db.close()
        return result
    }

    fun eliminarSuscripcion(id: Int): Int {
        val db = dbHelper.writableDatabase
        val result = db.delete(DatabaseHelper.TABLE_SUSCRIPCION, "${DatabaseHelper.COLUMN_SUSCRIPCION_ID}=?", arrayOf(id.toString()))
        db.close()
        return result
    }
}
