package com.android.fintech_demo.presentation.fragments.my_cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.fintech_demo.databinding.FragmentMyCardsBinding
import com.android.fintech_demo.domain.model.CardModel
import com.android.fintech_demo.presentation.AppApplication
import com.android.fintech_demo.presentation.MainActivity
import com.android.fintech_demo.presentation.fragments.my_cards.adapter.MyCardRecyclerAdapter

class MyCardsFragment : Fragment() {

    companion object {
        private const val PARAM_CARD_LIST = "PARAM_CARD_LIST"

        fun newInstance(cardList: List<CardModel>): MyCardsFragment {
            return MyCardsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PARAM_CARD_LIST, cardList as java.io.Serializable)
                }
            }
        }
    }

    private lateinit var binding: FragmentMyCardsBinding
    private val cardList by lazy {
        arguments?.getSerializable(PARAM_CARD_LIST) as List<CardModel>
    }
    private lateinit var cardsAdapter: MyCardRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((requireActivity() as MainActivity).application as AppApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { (requireActivity() as MainActivity).onBackPressed() }
        setupRecyclerView(cardList)
    }

    private fun setupRecyclerView(cardList: List<CardModel>) = with(binding.recyclerView) {
        cardsAdapter = MyCardRecyclerAdapter(cardList, ::onCardSelected)
        adapter = cardsAdapter
        addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
    }

    private fun onCardSelected(cardModel: CardModel) {
        cardsAdapter.onItemSelected(cardModel)
        (requireActivity() as MainActivity).onCardSelected()
    }
}