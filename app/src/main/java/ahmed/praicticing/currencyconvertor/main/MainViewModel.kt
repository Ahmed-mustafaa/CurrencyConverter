package ahmed.praicticing.currencyconvertor.main

import ahmed.praicticing.currencyconvertor.Util.DispatcherProvider
import ahmed.praicticing.currencyconvertor.Util.Resources
import ahmed.praicticing.currencyconvertor.data2.Rates
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.round
class MainViewModel  (
    private val mainRepository: MainRepository,
private val dispatcher: DispatcherProvider
): ViewModel() {


    sealed class CurrencyEvent {
        class Success(val resultText: String) : CurrencyEvent()
        class Failure(val errorText: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amount: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        val fromAmount = amount.toFloatOrNull()
        if (fromAmount == null) {
            Log.d("Amount is Null", "NULLLLLLLLLL ")
            _conversion.value = CurrencyEvent.Failure("Not a valid amount ")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _conversion.value = CurrencyEvent.Loading
            when (val ratesResponse = mainRepository.getRates(fromCurrency)) {
                is Resources.error -> _conversion.value =
                    CurrencyEvent.Failure(ratesResponse.message!!)

                is Resources.success -> {
                    val rates = ratesResponse.data!!.rates
                    val rate = getRatesForCurrency(toCurrency, rates)
                    if (rate == null) {
                        _conversion.value = CurrencyEvent.Failure("Unexpected error")
                    } else {
                        val convertedCurrency = round(fromAmount * rate * 100) / 100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
            }
        }
    }

    private fun getRatesForCurrency(currency: String, rates: Rates):Double {
        return when (currency) {
            "CAD" -> rates.cAD
            "HKD" -> rates.hKD
            "ISK" -> rates.iSK
            "EUR" -> rates.eUR
            "PHP" -> rates.pHP
            "DKK" -> rates.dKK
            "HUF" -> rates.hUF
            "CZK" -> rates.cZK
            "AUD" -> rates.aUD
            "RON" -> rates.rON
            "SEK" -> rates.sEK
            "IDR" -> rates.iDR
            "INR" -> rates.iNR
            "BRL" -> rates.bRL
            "RUB" -> rates.rUB
            "HRK" -> rates.hRK
            "JPY" -> rates.jPY
            "THB" -> rates.tHB
            "CHF" -> rates.cHF
            "SGD" -> rates.sGD
            "PLN" -> rates.pLN
            "BGN" -> rates.bGN
            "CNY" -> rates.cNY
            "NOK" -> rates.nOK
            "NZD" -> rates.nZD
            "ZAR" -> rates.zAR
            "USD" -> rates.uSD
            "MXN" -> rates.mXN
            "ILS" -> rates.iLS
            "GBP" -> rates.gBP
            "KRW" -> rates.kRW
            "MYR" -> rates.mYR
            "EGP" -> rates.eGP
            "SAR" -> rates.sAR
            "AED" -> rates.aED
            "KWD" -> rates.kWD
            else -> 0.0
        }
    }
}