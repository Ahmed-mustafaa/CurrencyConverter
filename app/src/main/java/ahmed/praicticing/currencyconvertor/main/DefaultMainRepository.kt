package ahmed.praicticing.currencyconvertor.main

import ahmed.praicticing.currencyconvertor.Util.Resources
import ahmed.praicticing.currencyconvertor.data.models.CurrencyAPI
import ahmed.praicticing.currencyconvertor.data.models.CurrencyResponse
import jakarta.inject.Inject

class DefaultMainRepository @Inject constructor(
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
                Resources.error(response.message())
            }
        }catch (e:Exception){
            Resources.error(e.message?:"An error occured")
        }
    }
}