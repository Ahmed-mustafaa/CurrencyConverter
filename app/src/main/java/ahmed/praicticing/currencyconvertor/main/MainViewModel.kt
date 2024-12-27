package ahmed.praicticing.currencyconvertor.main

import ahmed.praicticing.currencyconvertor.Util.DispatcherProvider
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
private val dispatcher: DispatcherProvider
): ViewModel(){



sealed class CurrencyEvent{
    class Success(val resultText:String):CurrencyEvent()
    class Failure(val errorText:String):CurrencyEvent()
    object Loading:CurrencyEvent()
    object Empty:CurrencyEvent()
}
    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion : StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amount:String,
        fromCurrency:String,
        toCurrency:String
    ){
        val fromAmount = amount.toFloatOrNull()
        if(fromAmount == null ){
            _conversion.value = CurrencyEvent.Failure("Not a valid amount ")
            return
        }
        viewModelScope.launch (Dispatchers.IO){
            _conversion.value = CurrencyEvent.Loading
        }
    }
}