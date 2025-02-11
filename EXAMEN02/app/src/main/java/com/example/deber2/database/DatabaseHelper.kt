package com.example.deber2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_SERVICIO)
        db.execSQL(CREATE_TABLE_SUSCRIPCION)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SERVICIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUSCRIPCION")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "servicio.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_SERVICIO = "servicio"
        const val COLUMN_SERVICIO_ID = "id"
        const val COLUMN_SERVICIO_NOMBRE = "nombre"
        const val COLUMN_SERVICIO_CANTIDAD = "cantidadSuscripciones"
        const val COLUMN_SERVICIO_DESCRIPCION = "descripcion"

        const val TABLE_SUSCRIPCION = "suscripcion"
        const val COLUMN_SUSCRIPCION_ID = "id"
        const val COLUMN_SUSCRIPCION_NOMBRE = "nombre"
        const val COLUMN_SUSCRIPCION_DURACION = "duracion"
        const val COLUMN_SUSCRIPCION_COSTO = "costo"
        const val COLUMN_SUSCRIPCION_SERVICIO_ID = "servicio_id"

        private const val CREATE_TABLE_SERVICIO = """
            CREATE TABLE $TABLE_SERVICIO (
                $COLUMN_SERVICIO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_SERVICIO_NOMBRE TEXT NOT NULL,
                $COLUMN_SERVICIO_CANTIDAD INTEGER NOT NULL,
                $COLUMN_SERVICIO_DESCRIPCION TEXT NOT NULL
            )
        """

        private const val CREATE_TABLE_SUSCRIPCION = """
            CREATE TABLE $TABLE_SUSCRIPCION (
                $COLUMN_SUSCRIPCION_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_SUSCRIPCION_NOMBRE TEXT NOT NULL,
                $COLUMN_SUSCRIPCION_DURACION INTEGER NOT NULL,
                $COLUMN_SUSCRIPCION_COSTO REAL NOT NULL,
                $COLUMN_SUSCRIPCION_SERVICIO_ID INTEGER NOT NULL,
                FOREIGN KEY($COLUMN_SUSCRIPCION_SERVICIO_ID) REFERENCES $TABLE_SERVICIO($COLUMN_SERVICIO_ID) ON DELETE CASCADE
            )
        """
    }
}