package com.example.controledegastos.models

enum class TransactionType {
    EXPENSE, // Despesa
    INCOME   // Receita
}
data class Transaction(
    var id: Long = 0,
    var description: String,
    var amount: Double,
    var category: String,
    var date: Long,
    var type: TransactionType // "expense" ou "income"
)

