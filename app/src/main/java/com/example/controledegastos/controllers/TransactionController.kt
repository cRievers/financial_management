package com.example.controledegastos.controllers

import android.content.Context
import com.example.controledegastos.models.Transaction
import com.example.controledegastos.models.TransactionType
import com.example.controledegastos.persistence.TransactionDAO

class TransactionController(context: Context) {

    private val transactionDAO = TransactionDAO(context)

    fun addTransaction(
        description: String,
        amount: Double,
        category: String,
        date: Long,
        type: TransactionType = TransactionType.EXPENSE
    ): Long {
        // Exemplo de validações
        require(description.isNotEmpty()) { "Descrição não pode ser vazia." }
        require(amount >= 0) { "Valor não pode ser negativo." }

        val transaction = Transaction(
            id = 0,
            description = description,
            amount = amount,
            category = category,
            date = date,
            type = type
        )
        return transactionDAO.insert(transaction)
    }

    fun listTransactions(): List<Transaction> {
        return transactionDAO.getAll()
    }

    fun updateTransactions(transaction: Transaction): Int {
        // Validar expense se necessário
        return transactionDAO.update(transaction)
    }

    fun deleteTransactions(id: Long): Int {
        return transactionDAO.delete(id)
    }
}
