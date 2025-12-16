package com.example.controledegastos.controllers

import android.content.Context
import com.example.controledegastos.models.Expense
import com.example.controledegastos.persistence.ExpenseDAO

class ExpenseController(context: Context) {

    private val expenseDAO = ExpenseDAO(context)

    fun addExpense(
        description: String,
        amount: Double,
        category: String,
        date: Long,
        type: String
    ): Long {
        require(description.isNotEmpty()) { "Descrição não pode ser vazia." }
        require(amount >= 0) { "Valor não pode ser negativo." }

        val expense = Expense(
            id = 0,
            description = description,
            amount = amount,
            category = category,
            date = date,
            type = type
        )

        return expenseDAO.insert(expense)
    }


    fun listExpenses(): List<Expense> {
        return expenseDAO.getAll()
    }

    fun updateExpense(expense: Expense): Int {
        // Validar expense se necessário
        return expenseDAO.update(expense)
    }

    fun deleteExpense(id: Long): Int {
        return expenseDAO.delete(id)
    }
}
