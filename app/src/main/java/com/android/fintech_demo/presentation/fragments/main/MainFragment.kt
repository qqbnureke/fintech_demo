package com.android.fintech_demo.presentation.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.fintech_demo.R
import com.android.fintech_demo.databinding.FragmentMainBinding
import com.android.fintech_demo.domain.model.CardModel
import com.android.fintech_demo.presentation.AppApplication
import com.android.fintech_demo.presentation.MainActivity
import com.android.fintech_demo.presentation.fragments.main.adapter.CurrencyRecyclerAdapter
import com.android.fintech_demo.presentation.fragments.main.adapter.GridSpacingItemDecoration
import com.android.fintech_demo.presentation.fragments.main.adapter.TransactionRecyclerAdapter
import com.android.fintech_demo.presentation.utils.Currency
import javax.inject.Inject

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private var selectedCurrency = Currency.GBP
    private lateinit var transactionAdapter: TransactionRecyclerAdapter
    private lateinit var currencyAdapter: CurrencyRecyclerAdapter
    private lateinit var cardList: List<CardModel>

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((requireActivity() as MainActivity).application as AppApplication).component.inject(this)
    }

    fun onCallback() {
        processViews(cardList.first { it.isSelected })
        transactionAdapter.onCurrencyChanged(selectedCurrency)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.itemsMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is MainViewModel.CardsState.Loading -> showLoading()
                is MainViewModel.CardsState.Cards -> {
                    hideLoading()
                    processViews(it.cardList.first().apply { isSelected = true })
                }
                else -> hideLoading()
            }
        }

        viewModel.navigationMutableLiveData.observe(viewLifecycleOwner) {
            cardList = it
            (requireActivity() as MainActivity).onMainCardClicked(cardList)
        }
        viewModel.cardCurrencyMutableLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.layoutCard.tvBalanceInCurrency.text =
                    getString(R.string.card_balance_in_currency, selectedCurrency.sign, it)
                transactionAdapter.onCurrencyChanged(selectedCurrency)
            }
        }
    }

    private fun processViews(cardModel: CardModel) {
        processCard(cardModel)
        processTransactionHistory(cardModel)
        processCurrencyToggle()
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun processCard(cardModel: CardModel) = with(binding.layoutCard) {
        ivCardType.setImageResource(cardModel.cardTypeIcon)
        tvCardNumber.text = cardModel.cardNumber
        tvCardholderName.text = cardModel.cardholderName
        tvValidDate.text = cardModel.validDate
        tvBalance.text = getString(R.string.card_amount, cardModel.balance)
        ivCardType.setOnClickListener { viewModel.onCardTypeClicked() }
    }

    private fun processTransactionHistory(cardModel: CardModel) =
        with(binding.layoutTransactionHistory) {
            transactionAdapter = TransactionRecyclerAdapter(cardModel.transactionHistory)
            recyclerViewHistory.adapter = transactionAdapter
        }

    private fun processCurrencyToggle() = with(binding.layoutSelectCurrency.recyclerViewCurrency) {
        currencyAdapter = CurrencyRecyclerAdapter(currencyList, ::onCurrencyChanged)
        adapter = currencyAdapter
        addItemDecoration(GridSpacingItemDecoration((8 * context.resources.displayMetrics.density).toInt()))
    }

    private val currencyList by lazy {
        listOf(
            CurrencySelectorModel(
                Currency.GBP, getString(R.string.currency_gbp), getString(R.string.sign_gbp)
            ).apply { isSelected = true },
            CurrencySelectorModel(
                Currency.EUR, getString(R.string.currency_euro), getString(R.string.sign_euro)
            ),
            CurrencySelectorModel(
                Currency.RUB, getString(R.string.currency_ruble), getString(R.string.sign_ruble)
            ),
        )
    }

    private fun onCurrencyChanged(model: CurrencySelectorModel) {
        selectedCurrency = model.currency
        viewModel.onCurrencyChanged(selectedCurrency)
        currencyAdapter.onItemSelected(model)
    }
}