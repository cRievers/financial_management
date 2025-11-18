package com.example.controledegastos.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.example.controledegastos.R
import com.example.controledegastos.controllers.TransactionController
import com.example.controledegastos.models.TransactionType

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var controller: TransactionController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        controller = TransactionController(this)

        // Referências para as Views
        val rgTransactionType = findViewById<RadioGroup>(R.id.rgTransactionType)
        val edtDescription = findViewById<EditText>(R.id.edtDescription)
        val edtAmount = findViewById<EditText>(R.id.edtAmount)
        val edtCategory = findViewById<EditText>(R.id.edtCategory)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val selectedId = rgTransactionType.checkedRadioButtonId
            val type = if (selectedId == R.id.rbIncome) TransactionType.INCOME else TransactionType.EXPENSE
            val description = edtDescription.text.toString()
            val amount = edtAmount.text.toString().toDoubleOrNull() ?: 0.0
            val category = edtCategory.text.toString()
            // Pegando a data/hora atual em milissegundos
            val date = System.currentTimeMillis()

            if (description.isBlank() || category.isBlank()) {
                Toast.makeText(this, "Descrição e categoria são obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            controller.addTransaction(description, amount, category, date, type)
            val message = if (type == TransactionType.INCOME) "Receita adicionada com sucesso!" else "Despesa adicionada com sucesso!"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            finish() // Fecha a Activity e retorna à anterior
        }
    }
}
