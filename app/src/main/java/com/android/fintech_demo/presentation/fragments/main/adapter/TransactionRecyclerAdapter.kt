package com.android.fintech_demo.presentation.fragments.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.fintech_demo.databinding.ItemTransactionBinding
import com.android.fintech_demo.domain.model.TransactionHistoryModel
import com.android.fintech_demo.presentation.utils.Currency
import com.android.fintech_demo.presentation.utils.CurrencyUtils

class TransactionRecyclerAdapter(private val items: List<TransactionHistoryModel>) :
    RecyclerView.Adapter<TransactionRecyclerAdapter.TransactionViewHolder>() {

    fun onCurrencyChanged(currency: Currency) {
        items.forEach {
            val res =
                String.format("%.2f", CurrencyUtils.cardCurrency(currency, it.amount.toFloat()))
            it.currencyAmount = currency.sign.plus(res)
        }
        notifyItemRangeChanged(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: TransactionHistoryModel) = with(binding) {
            ivLogo.load(model.iconUrl)
            tvName.text = model.title
            tvDate.text = model.date
            tvAmount.text = "$".plus(model.amount).replace("-", "")
            tvAmountInCurrency.text = model.currencyAmount.replace("-", "")
        }
    }
}