package com.example.controledegastos.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.controledegastos.R
import com.example.controledegastos.controllers.ExpenseController

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var controller: ExpenseController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        controller = ExpenseController(this)

        // Referências para as Views
        val edtDescription = findViewById<EditText>(R.id.edtDescription)
        val edtAmount = findViewById<EditText>(R.id.edtAmount)
        val edtCategory = findViewById<EditText>(R.id.edtCategory)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val description = edtDescription.text.toString()
            val amount = edtAmount.text.toString().toDoubleOrNull() ?: 0.0
            val category = edtCategory.text.toString()
            // Pegando a data/hora atual em milissegundos
            val date = System.currentTimeMillis()

            controller.addExpense(description, amount, category, date)
            Toast.makeText(this, "Despesa adicionada com sucesso!", Toast.LENGTH_SHORT).show()
            finish() // Fecha a Activity e retorna à anterior
        }
    }
}
