package com.example.controledegastos.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.controledegastos.R
import com.example.controledegastos.adapters.TransactionAdapter
import com.example.controledegastos.controllers.TransactionController
import com.example.controledegastos.models.Transaction

class ListTransactionActivity : AppCompatActivity() {

    private lateinit var transactionController: TransactionController
    private lateinit var transactionAdapter: TransactionAdapter
    private var transactionList: MutableList<Transaction> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_transaction)

        // Instancia o Controller e carrega os dados
        transactionController = TransactionController(this)
        transactionList = transactionController.listTransactions().toMutableList()

        // Configura o Adapter
        transactionAdapter = TransactionAdapter(this, transactionList)

        val transactionsListView = findViewById<ListView>(R.id.listViewTransactions)
        transactionsListView.adapter = transactionAdapter

        // Quando o usuário clica em um item da lista, abre a tela de edição
        transactionsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedTransaction = transactionList[position]
            val intent = Intent(this, EditTransactionActivity::class.java)
            intent.putExtra("expenseId", selectedTransaction.id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Atualiza a lista ao voltar para esta tela
        transactionList.clear()
        transactionList.addAll(transactionController.listTransactions())
        transactionAdapter.notifyDataSetChanged()
    }
}
