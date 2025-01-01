package ahmed.praicticing.currencyconvertor.main

import ahmed.praicticing.currencyconvertor.Util.Resources
import ahmed.praicticing.currencyconvertor.data2.CurrencyAPI
import ahmed.praicticing.currencyconvertor.data2.CurrencyResponse
import android.util.Log

class DefaultMainRepository  (
    private val api: CurrencyAPI
): MainRepository
{
    override suspend fun getRates(base: String): Resources<CurrencyResponse> {
        return try {
            val response = api.getRates(base)
            val result = response.body()
            if(response.isSuccessful && result!=null){
                Resources.success(result)
            }else{
                Log.d("Error", "getRates:Errror from THE API")
                Resources.error(response.message())
            }
        }catch (e:Exception){
            Resources.error(e.message?:"An error occured")
        }
    }
}