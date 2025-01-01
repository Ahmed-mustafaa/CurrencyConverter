package ahmed.praicticing.currencyconvertor.main

import ahmed.praicticing.currencyconvertor.Util.DispatcherProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class mainViewModelFactory(
    private val repository: DefaultMainRepository,
    private val dispatcher: DispatcherProvider
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
        return MainViewModel(repository,dispatcher) as T
    }
    else{
        throw IllegalArgumentException("Unknown ViewModel class")
    }
    }

}