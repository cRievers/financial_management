package com.example.controledegastos.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.controledegastos.R
import com.example.controledegastos.exports.CSVExporter
import com.example.controledegastos.models.Expense
import com.example.controledegastos.persistence.ExpenseDAO
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddExpense = findViewById<Button>(R.id.btnAddExpense)
        val btnAddIncome = findViewById<Button>(R.id.btnAddIncome)     // novo botão
        val btnListExpense = findViewById<Button>(R.id.btnListExpense)
        val btnExportCSV = findViewById<Button>(R.id.btnExportCSV)

        btnAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        // abre a tela de cadastro de receita
        btnAddIncome.setOnClickListener {
            val intent = Intent(this, AddIncomeActivity::class.java)
            startActivity(intent)
        }

        btnListExpense.setOnClickListener {
            val intent = Intent(this, ListExpenseActivity::class.java)
            startActivity(intent)
        }

        btnExportCSV.setOnClickListener {
            exportCSV()
        }

        // Atualiza o resumo quando a tela é criada
        atualizarResumo()
    }

    override fun onResume() {
        super.onResume()
        // Atualiza o resumo sempre que volta para a tela inicial
        atualizarResumo()
    }

    private fun atualizarResumo() {
        val txtBalance = findViewById<TextView>(R.id.txtBalance)
        val txtTotalIncome = findViewById<TextView>(R.id.txtTotalIncome)
        val txtTotalExpense = findViewById<TextView>(R.id.txtTotalExpense)

        // Define a cor do texto (preto) para aparecer bem sobre o CardView branco
        val summaryTextColor = ContextCompat.getColor(this, android.R.color.black)
        txtBalance.setTextColor(summaryTextColor)
        txtTotalIncome.setTextColor(summaryTextColor)
        txtTotalExpense.setTextColor(summaryTextColor)

        val dao = ExpenseDAO(this)
        val allExpenses: List<Expense> = dao.getAll()

        // Pressupõe que Expense tem o campo "type" com "income" ou "expense"
        val totalIncome = allExpenses
            .filter { it.type == "income" }
            .sumOf { it.amount }

        val totalExpense = allExpenses
            .filter { it.type == "expense" }
            .sumOf { it.amount }

        val balance = totalIncome - totalExpense

        txtTotalIncome.text = "Receitas: R$ %.2f".format(totalIncome)
        txtTotalExpense.text = "Despesas: R$ %.2f".format(totalExpense)
        txtBalance.text = "Saldo: R$ %.2f".format(balance)
    }

    private fun exportCSV() {
        val dao = ExpenseDAO(this)
        val expenses = dao.getAll() // obtém todas as transações do banco

        if (expenses.isEmpty()) {
            Toast.makeText(this, "Nenhuma despesa para exportar!", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val csvFile = CSVExporter.exportExpensesToCSV(this, expenses)
            Toast.makeText(this, "Arquivo exportado para: ${csvFile.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Toast.makeText(this, "Erro ao exportar CSV: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
