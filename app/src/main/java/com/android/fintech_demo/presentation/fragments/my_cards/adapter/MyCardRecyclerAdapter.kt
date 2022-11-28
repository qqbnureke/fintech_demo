package com.android.fintech_demo.presentation.fragments.my_cards.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.fintech_demo.databinding.ItemMyCardBinding
import com.android.fintech_demo.domain.model.CardModel

class MyCardRecyclerAdapter(
    private val items: List<CardModel>, private val onItemClick: (CardModel) -> Unit
) : RecyclerView.Adapter<MyCardRecyclerAdapter.MyCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCardViewHolder {
        val binding = ItemMyCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyCardViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MyCardViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun onItemSelected(model: CardModel) {
        val oldIndex = items.indexOfFirst { it.isSelected }
        val newIndex = items.indexOfFirst { it.cardNumber == model.cardNumber }
        items[oldIndex].isSelected = false
        notifyItemChanged(oldIndex)
        items[newIndex].isSelected = true
        notifyItemChanged(newIndex)
    }

    class MyCardViewHolder(
        private val binding: ItemMyCardBinding, private val onItemClick: (CardModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CardModel) = with(binding) {
            root.setOnClickListener { onItemClick(model) }
            tvCardNumber.text = model.cardNumber
            ivCardType.setImageResource(model.cardTypeIcon)
            ivIsSelected.visibility = if (model.isSelected) View.VISIBLE else View.GONE
        }
    }
}