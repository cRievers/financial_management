package com.example.controledegastos.exports

import android.content.Context
import com.example.controledegastos.models.Expense
import java.io.File
import java.io.FileWriter
import java.io.IOException

object CSVExporter {

    @Throws(IOException::class)
    fun exportExpensesToCSV(context: Context, expenses: List<Expense>): File {
        val exportDir = File(context.getExternalFilesDir(null), "exports")
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }

        val csvFile = File(exportDir, "despesas.csv")
        FileWriter(csvFile).use { writer ->
            writer.append("ID,Description,Amount,Category,Date\n")
            for (e in expenses) {
                writer.append(e.id.toString()).append(",")
                writer.append(e.description).append(",")
                writer.append(e.amount.toString()).append(",")
                writer.append(e.category).append(",")
                writer.append(e.date.toString()).append("\n")
            }
            writer.flush()
        }
        return csvFile
    }
}
