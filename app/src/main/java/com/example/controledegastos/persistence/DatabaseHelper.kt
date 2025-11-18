package com.example.controledegastos.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_TRANSACTION)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTION")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "gastos.db"
        private const val DATABASE_VERSION = 2


        const val TABLE_TRANSACTION = "transaction"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_DATE = "date"
        const val COLUMN_TYPE = "type"

        private val CREATE_TABLE_TRANSACTION = """
            CREATE TABLE $TABLE_TRANSACTION (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_AMOUNT REAL,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_DATE INTEGER,
                $COLUMN_TYPE TEXT 
            );
        """
    }
}
