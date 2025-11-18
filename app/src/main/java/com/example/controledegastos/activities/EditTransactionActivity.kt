package com.example.controledegastos.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.example.controledegastos.R
import com.example.controledegastos.controllers.TransactionController
import com.example.controledegastos.models.Transaction
import com.example.controledegastos.models.TransactionType

class EditTransactionActivity : AppCompatActivity() {

    private lateinit var controller: TransactionController
    private var currentTransaction: Transaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaction)

        controller = TransactionController(this)

        // Recupera o ID da despesa selecionada
        val transId = intent.getLongExtra("transId", -1)
        if (transId != -1L) {
            // Busca a despesa no banco
            val allTransactions = controller.listTransactions()
            currentTransaction = allTransactions.find { it.id == transId }
        }

        // Referências das Views
        val edtDescription = findViewById<EditText>(R.id.edtDescription)
        val edtAmount = findViewById<EditText>(R.id.edtAmount)
        val edtCategory = findViewById<EditText>(R.id.edtCategory)
        val rgTransactionType = findViewById<RadioGroup>(R.id.rgTransactionType)
        val selectedId = rgTransactionType.checkedRadioButtonId
        val type = if (selectedId == R.id.rbIncome) TransactionType.INCOME else TransactionType.EXPENSE
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        // Se encontrou o objeto, exibe nos campos
        currentTransaction?.let { transaction ->
            edtDescription.setText(transaction.description)
            edtAmount.setText(transaction.amount.toString())
            edtCategory.setText(transaction.category)
            rgTransactionType.check(if(transaction.type == TransactionType.INCOME) R.id.rbIncome else R.id.rbExpense)
        }

        // Botão para salvar alterações
        btnUpdate.setOnClickListener {
            currentTransaction?.let { transaction ->
                transaction.description = edtDescription.text.toString()
                transaction.amount = edtAmount.text.toString().toDoubleOrNull() ?: 0.0
                transaction.category = edtCategory.text.toString()
                transaction.type = type

                controller.updateTransactions(transaction)
                Toast.makeText(this, "Transação atualizada!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Botão para excluir
        btnDelete.setOnClickListener {
            currentTransaction?.let { transaction ->
                controller.deleteTransactions(transaction.id)
                Toast.makeText(this, "Transação excluída!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
