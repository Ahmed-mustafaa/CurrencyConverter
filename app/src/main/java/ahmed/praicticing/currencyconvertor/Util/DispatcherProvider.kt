package ahmed.praicticing.currencyconvertor.Util
import kotlinx.coroutines.*
interface DispatcherProvider {
val main: CoroutineDispatcher
val io: CoroutineDispatcher
val default: CoroutineDispatcher
val unconfined: CoroutineDispatcher
}
