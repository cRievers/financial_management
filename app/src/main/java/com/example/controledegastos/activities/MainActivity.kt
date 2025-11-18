package com.example.controledegastos.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.controledegastos.R
import com.example.controledegastos.persistence.TransactionDAO
import com.example.controledegastos.exports.CSVExporter
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnListTransactions = findViewById<Button>(R.id.btnListTransactions)
        val btnExportCSV = findViewById<Button>(R.id.btnExportCSV)
        val fabAddTransaction = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAddTransaction)

        btnListTransactions.setOnClickListener {
            val intent = Intent(this, ListTransactionActivity::class.java)
            startActivity(intent)
        }

        btnExportCSV.setOnClickListener {
            exportCSV()
        }

        fabAddTransaction.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun exportCSV() {
        val dao = TransactionDAO(this)
        val expenses = dao.getAll() // Obtém todas as transações do banco

        if (expenses.isEmpty()) {
            Toast.makeText(this, "Nada para exportar!", Toast.LENGTH_SHORT).show()
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
