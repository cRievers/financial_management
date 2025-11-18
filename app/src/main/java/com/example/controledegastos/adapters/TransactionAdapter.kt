package com.example.controledegastos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.controledegastos.R
import com.example.controledegastos.models.Transaction
import com.example.controledegastos.models.TransactionType
import com.example.controledegastos.utils.DateUtils

class TransactionAdapter(
    private val context: Context,
    private val transactionList: List<Transaction>
) : BaseAdapter() {

    override fun getCount(): Int = transactionList.size

    override fun getItem(position: Int): Any = transactionList[position]

    override fun getItemId(position: Int): Long = transactionList[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_expense, parent, false)

        val transaction = transactionList[position]

        val txtDescription = view.findViewById<TextView>(R.id.txtDescription)
        val txtAmount = view.findViewById<TextView>(R.id.txtAmount)
        val txtCategory = view.findViewById<TextView>(R.id.txtCategory)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)

        txtDescription.text = transaction.description
        txtAmount.text = String.format("R$ %.2f", transaction.amount)
        if (transaction.type == TransactionType.INCOME) {
            txtAmount.setTextColor(ContextCompat.getColor(context, R.color.income_color))
        } else {
            txtAmount.setTextColor(ContextCompat.getColor(context, R.color.expense_color))
        }
        txtCategory.text = transaction.category
        txtDate.text = DateUtils.formatDate(transaction.date)

        return view
    }
}
