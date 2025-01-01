package ahmed.praicticing.currencyconvertor.data2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {
    @GET("/v1/latest?access_key=213682d1765100e21a93327adcdf580b&format=1")
    suspend fun getRates(
        @Query("base") base: String
    ):Response<CurrencyResponse>
}