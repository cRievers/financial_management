package com.example.controledegastos.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.controledegastos.R
import com.example.controledegastos.adapters.ExpenseAdapter
import com.example.controledegastos.controllers.ExpenseController
import com.example.controledegastos.models.Expense

class ListExpenseActivity : AppCompatActivity() {

    private lateinit var expenseController: ExpenseController
    private lateinit var expenseAdapter: ExpenseAdapter
    private var expenseList: MutableList<Expense> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_expense)

        // Instancia o Controller e carrega os dados
        expenseController = ExpenseController(this)
        expenseList = expenseController.listExpenses().toMutableList()

        // Configura o Adapter
        expenseAdapter = ExpenseAdapter(this, expenseList)

        val listViewExpenses = findViewById<ListView>(R.id.listViewExpenses)
        listViewExpenses.adapter = expenseAdapter

        // Quando o usuário clica em um item da lista, abre a tela de edição
        listViewExpenses.setOnItemClickListener { _, _, position, _ ->
            val selectedExpense = expenseList[position]
            val intent = Intent(this, EditExpenseActivity::class.java)
            intent.putExtra("expenseId", selectedExpense.id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Atualiza a lista ao voltar para esta tela
        expenseList.clear()
        expenseList.addAll(expenseController.listExpenses())
        expenseAdapter.notifyDataSetChanged()
    }
}
