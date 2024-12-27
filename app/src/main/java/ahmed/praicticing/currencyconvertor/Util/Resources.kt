package ahmed.praicticing.currencyconvertor.Util

sealed class Resources<T>(val data:T?, val message:String?) {
    class success<T>(data: T?): Resources<T>(data,null)
    class error<T>(message: String?): Resources<T>(null,message)

}