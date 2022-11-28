package com.android.fintech_demo.presentation.fragments.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.fintech_demo.domain.Repository
import com.android.fintech_demo.domain.model.CardModel
import com.android.fintech_demo.presentation.utils.Currency
import com.android.fintech_demo.presentation.utils.CurrencyUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    sealed class CardsState {
        object Loading : CardsState()
        object Error : CardsState()
        class Cards(val cardList: List<CardModel>) : CardsState()
    }

    val itemsMutableLiveData = MutableLiveData<CardsState>()
    val cardCurrencyMutableLiveData = MutableLiveData<Float>()
    val navigationMutableLiveData = MutableLiveData<List<CardModel>>()

    init {
        getItems()
        getCurrencies()
    }

    private fun getItems() {
        CoroutineScope(Dispatchers.IO).launch {
            itemsMutableLiveData.postValue(CardsState.Loading)
            try {
                val cardList = repository.getCards()
                itemsMutableLiveData.postValue(CardsState.Cards(cardList))
                CurrencyUtils.cardList = cardList
                onCurrencyChanged()
            } catch (_: Exception) {
                itemsMutableLiveData.postValue(CardsState.Error)
            }
        }
    }

    private fun getCurrencies() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val currencyModel = repository.getCurrencies()
                CurrencyUtils.currencyModel = currencyModel
                onCurrencyChanged()
            } catch (_: Exception) {
            }
        }
    }

    fun onCardTypeClicked() {
        if (itemsMutableLiveData.value is CardsState.Cards) {
            navigationMutableLiveData.postValue((itemsMutableLiveData.value as CardsState.Cards).cardList)
        }
    }

    fun onCurrencyChanged(currency: Currency = Currency.GBP) {
        cardCurrencyMutableLiveData.postValue(CurrencyUtils.cardCurrency(currency))
    }
}

class MainViewModelFactory @Inject constructor(
    private val map: Map<Class<*>, @JvmSuppressWildcards ViewModel>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return map[modelClass] as T
    }
}