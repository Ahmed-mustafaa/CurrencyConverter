package ahmed.praicticing.currencyconvertor

import ahmed.praicticing.currencyconvertor.Util.DispatcherProvider
import ahmed.praicticing.currencyconvertor.data2.retrofit
import ahmed.praicticing.currencyconvertor.databinding.ActivityMainBinding
import ahmed.praicticing.currencyconvertor.main.DefaultMainRepository
import ahmed.praicticing.currencyconvertor.main.MainViewModel
import ahmed.praicticing.currencyconvertor.main.mainViewModelFactory
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.isVisible

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val repository = DefaultMainRepository(retrofit.provideCurrencyApi())
    private val dispatcher: DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
                get() = Dispatchers.IO
    }
   val mainViewModelFactory = mainViewModelFactory(repository,dispatcher)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
viewModel =mainViewModelFactory.create(MainViewModel::class.java)
        binding.Convert.setOnClickListener {
            viewModel.convert(
            binding.amount.text.toString(),
            binding.spFrom.selectedItem.toString(),
            binding.spTo.selectedItem.toString()
            )
        }
        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when (event) {
                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.progress.isVisible = false
                        binding.Result.setTextColor(Color.BLACK)
                        binding.Result.text = event.resultText
                    }

                    is MainViewModel.CurrencyEvent.Failure -> {
                        binding.progress.isVisible = false
                        binding.Result.text = event.errorText
                        binding.Result.setTextColor(Color.BLACK)
                    }

                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progress.isVisible = true

                    }

                    else -> Unit

                }
            }

        }
    }
}