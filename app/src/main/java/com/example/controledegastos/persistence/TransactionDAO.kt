package com.example.controledegastos.persistence

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.controledegastos.models.Transaction
import com.example.controledegastos.models.TransactionType


class TransactionDAO(context: Context) {

    private val helper: DatabaseHelper = DatabaseHelper(context)

    fun insert(transaction: Transaction): Long {
        val db: SQLiteDatabase = helper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_DESCRIPTION, transaction.description)
            put(DatabaseHelper.COLUMN_AMOUNT, transaction.amount)
            put(DatabaseHelper.COLUMN_CATEGORY, transaction.category)
            put(DatabaseHelper.COLUMN_DATE, transaction.date)
            put(DatabaseHelper.COLUMN_TYPE, transaction.type.toString())
        }
        val id = db.insert(DatabaseHelper.TABLE_TRANSACTION, null, values)
        db.close()
        return id
    }

    fun getAll(): List<Transaction> {
        val list = mutableListOf<Transaction>()
        val db: SQLiteDatabase = helper.readableDatabase

        val cursor: Cursor? = db.query(
            DatabaseHelper.TABLE_TRANSACTION,
            null, null, null, null, null, null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getLong(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                    val description = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION))
                    val amount = it.getDouble(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AMOUNT))
                    val category = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY))
                    val date = it.getLong(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE))
                    val type = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE))

                    val tipo: TransactionType
                    if (type == "EXPENSE") tipo = TransactionType.EXPENSE else tipo = TransactionType.INCOME

                    val transaction = Transaction(
                        id = id,
                        description = description,
                        amount = amount,
                        category = category,
                        date = date,
                        type = tipo
                    )
                    list.add(transaction)
                } while (it.moveToNext())
            }
        }

        db.close()
        return list
    }

    fun update(transaction: Transaction): Int {
        val db: SQLiteDatabase = helper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_DESCRIPTION, transaction.description)
            put(DatabaseHelper.COLUMN_AMOUNT, transaction.amount)
            put(DatabaseHelper.COLUMN_CATEGORY, transaction.category)
            put(DatabaseHelper.COLUMN_DATE, transaction.date)
            put(DatabaseHelper.COLUMN_TYPE, transaction.type.toString())
        }

        val selection = "${DatabaseHelper.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(transaction.id.toString())

        val rowsAffected = db.update(
            DatabaseHelper.TABLE_TRANSACTION,
            values,
            selection,
            selectionArgs
        )
        db.close()
        return rowsAffected
    }

    fun delete(id: Long): Int {
        val db: SQLiteDatabase = helper.writableDatabase
        val selection = "${DatabaseHelper.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        val rowsDeleted = db.delete(
            DatabaseHelper.TABLE_TRANSACTION,
            selection,
            selectionArgs
        )
        db.close()
        return rowsDeleted
    }
}
