package com.example.controledegastos.models

data class Expense(
    var id: Long = 0,
    var description: String,
    var amount: Double,
    var category: String,
    var date: Long
)

