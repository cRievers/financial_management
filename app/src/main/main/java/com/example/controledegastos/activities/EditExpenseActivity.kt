package com.example.controledegastos.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.controledegastos.R
import com.example.controledegastos.controllers.ExpenseController
import com.example.controledegastos.models.Expense

class EditExpenseActivity : AppCompatActivity() {

    private lateinit var controller: ExpenseController
    private var currentExpense: Expense? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_expense)

        controller = ExpenseController(this)

        // Recupera o ID da despesa selecionada
        val expenseId = intent.getLongExtra("expenseId", -1)
        if (expenseId != -1L) {
            // Busca a despesa no banco
            val allExpenses = controller.listExpenses()
            currentExpense = allExpenses.find { it.id == expenseId }
        }

        // Referências das Views
        val edtDescription = findViewById<EditText>(R.id.edtDescription)
        val edtAmount = findViewById<EditText>(R.id.edtAmount)
        val edtCategory = findViewById<EditText>(R.id.edtCategory)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        // Se encontrou a despesa, exibe nos campos
        currentExpense?.let { expense ->
            edtDescription.setText(expense.description)
            edtAmount.setText(expense.amount.toString())
            edtCategory.setText(expense.category)
        }

        // Botão para salvar alterações
        btnUpdate.setOnClickListener {
            currentExpense?.let { expense ->
                expense.description = edtDescription.text.toString()
                expense.amount = edtAmount.text.toString().toDoubleOrNull() ?: 0.0
                expense.category = edtCategory.text.toString()

                controller.updateExpense(expense)
                Toast.makeText(this, "Despesa atualizada!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Botão para excluir a despesa
        btnDelete.setOnClickListener {
            currentExpense?.let { expense ->
                controller.deleteExpense(expense.id)
                Toast.makeText(this, "Despesa excluída!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
