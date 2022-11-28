package com.android.fintech_demo.presentation.fragments.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.fintech_demo.databinding.ItemSelectCurrencyBinding
import com.android.fintech_demo.presentation.fragments.main.CurrencySelectorModel
import com.android.fintech_demo.presentation.fragments.main.getBackground
import com.android.fintech_demo.presentation.fragments.main.textColor

class CurrencyRecyclerAdapter(
    private val items: List<CurrencySelectorModel>,
    private val onCurrencySelected: (CurrencySelectorModel) -> Unit
) : RecyclerView.Adapter<CurrencyRecyclerAdapter.CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            ItemSelectCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding, onCurrencySelected)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun onItemSelected(model: CurrencySelectorModel) {
        val oldIndex = items.indexOfFirst { it.isSelected }
        val newIndex = items.indexOfFirst { it.currency == model.currency }
        items[oldIndex].isSelected = false
        notifyItemChanged(oldIndex)
        items[newIndex].isSelected = true
        notifyItemChanged(newIndex)
    }

    class CurrencyViewHolder(
        private val binding: ItemSelectCurrencyBinding,
        private val onCurrencySelected: (CurrencySelectorModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CurrencySelectorModel) = with(binding) {
            llGbp.background = ContextCompat.getDrawable(itemView.context, model.getBackground())
            tvCurrencyName.text = model.currencyName
            tvCurrencyName.setTextColor(ContextCompat.getColor(itemView.context, model.textColor()))
            tvSign.text = model.currencySign
            tvSign.setTextColor(ContextCompat.getColor(itemView.context, model.textColor()))
            root.setOnClickListener { onCurrencySelected(model) }
        }
    }
}