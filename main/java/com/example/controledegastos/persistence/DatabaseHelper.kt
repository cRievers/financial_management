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
        // Remove tabela antiga (caso exista) e a nova, depois recria
        db.execSQL("DROP TABLE IF EXISTS expense")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTION")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "gastos.db"
        // Versão aumentada para forçar recriação do banco com a nova estrutura
        private const val DATABASE_VERSION = 3

        const val TABLE_TRANSACTION = "transactions"

        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_DATE = "date"
        const val COLUMN_TYPE = "type"   // "expense" ou "income"

        private const val CREATE_TABLE_TRANSACTION = """
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
