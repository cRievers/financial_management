package com.example.controledegastos.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.controledegastos.R
import com.example.controledegastos.persistence.ExpenseDAO
import com.example.controledegastos.exports.CSVExporter
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddExpense = findViewById<Button>(R.id.btnAddExpense)
        val btnListExpense = findViewById<Button>(R.id.btnListExpense)
        val btnExportCSV = findViewById<Button>(R.id.btnExportCSV) // Novo botão

        btnAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        btnListExpense.setOnClickListener {
            val intent = Intent(this, ListExpenseActivity::class.java)
            startActivity(intent)
        }

        btnExportCSV.setOnClickListener {
            exportCSV()
        }
    }

    private fun exportCSV() {
        val dao = ExpenseDAO(this)
        val expenses = dao.getAll() // Obtém todas as despesas do banco

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
