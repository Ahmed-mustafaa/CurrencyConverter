package ahmed.praicticing.currencyconvertor.main

import ahmed.praicticing.currencyconvertor.Util.Resources
import ahmed.praicticing.currencyconvertor.data.models.CurrencyResponse

interface MainRepository {
    suspend fun getRates(base:String) : Resources<CurrencyResponse>
    // we have to define a resources sealed class to identify the response state   (success , failure..etc )
    // so we will be creating a Util object

}