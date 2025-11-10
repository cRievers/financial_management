package com.example.controledegastos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.controledegastos.R
import com.example.controledegastos.models.Expense
import com.example.controledegastos.utils.DateUtils

class ExpenseAdapter(
    private val context: Context,
    private val expenseList: List<Expense>
) : BaseAdapter() {

    override fun getCount(): Int = expenseList.size

    override fun getItem(position: Int): Any = expenseList[position]

    override fun getItemId(position: Int): Long = expenseList[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_expense, parent, false)

        val expense = expenseList[position]

        val txtDescription = view.findViewById<TextView>(R.id.txtDescription)
        val txtAmount = view.findViewById<TextView>(R.id.txtAmount)
        val txtCategory = view.findViewById<TextView>(R.id.txtCategory)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)

        txtDescription.text = expense.description
        txtAmount.text = String.format("R$ %.2f", expense.amount)
        txtCategory.text = expense.category
        txtDate.text = DateUtils.formatDate(expense.date)

        return view
    }
}
