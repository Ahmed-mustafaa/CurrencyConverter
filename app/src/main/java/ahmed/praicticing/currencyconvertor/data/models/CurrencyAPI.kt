package ahmed.praicticing.currencyconvertor.data.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {
    @GET("/latest")
    suspend fun getRates(
        @Query("base") base: String
    ):Response<CurrencyResponse>
}