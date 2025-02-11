package com.example.deber2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.deber2.models.Servicio

class ServicioDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun insertarServicio(servicio: Servicio): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_SERVICIO_NOMBRE, servicio.nombre)
            put(DatabaseHelper.COLUMN_SERVICIO_CANTIDAD, servicio.cantidadSuscripciones)
            put(DatabaseHelper.COLUMN_SERVICIO_DESCRIPCION, servicio.descripcion)
        }
        val id = db.insert(DatabaseHelper.TABLE_SERVICIO, null, values)
        db.close()
        return id
    }

    fun obtenerServicios(): List<Servicio> {
        val servicios = mutableListOf<Servicio>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_SERVICIO}", null)

        while (cursor.moveToNext()) {
            val servicio = Servicio(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERVICIO_ID)).toString(),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERVICIO_NOMBRE)),
                cantidadSuscripciones = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERVICIO_CANTIDAD))
                    .toString(),
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERVICIO_DESCRIPCION))
            )
            servicios.add(servicio)
        }
        cursor.close()
        db.close()
        return servicios
    }

    fun eliminarServicio(id: Int): Int {
        val db = dbHelper.writableDatabase
        val result = db.delete(DatabaseHelper.TABLE_SERVICIO, "${DatabaseHelper.COLUMN_SERVICIO_ID}=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun obtenerServicioPorId(id: Int): Servicio? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_SERVICIO} WHERE ${DatabaseHelper.COLUMN_SERVICIO_ID} = ?", arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            Servicio(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERVICIO_ID)).toString(),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERVICIO_NOMBRE)),
                cantidadSuscripciones = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERVICIO_CANTIDAD))
                    .toString(),
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERVICIO_DESCRIPCION))
            )
        } else {
            null
        }.also {
            cursor.close()
            db.close()
        }
    }

    fun actualizarServicio(id: Int, servicio: Servicio): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_SERVICIO_NOMBRE, servicio.nombre)
            put(DatabaseHelper.COLUMN_SERVICIO_CANTIDAD, servicio.cantidadSuscripciones)
            put(DatabaseHelper.COLUMN_SERVICIO_DESCRIPCION, servicio.descripcion)
        }
        val result = db.update(DatabaseHelper.TABLE_SERVICIO, values, "${DatabaseHelper.COLUMN_SERVICIO_ID}=?", arrayOf(id.toString()))
        db.close()
        return result
    }
}
