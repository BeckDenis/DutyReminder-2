package denis.beck.common

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.tryLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    doOnError: (Throwable.() -> Unit)? = null,
    doFinally: (() -> Unit)? = null,
    doOnLaunch: suspend CoroutineScope.() -> Unit,
): Job =
    launch(
        context = context + CoroutineExceptionHandler { _, error ->
            Timber.e(error)
            doOnError?.invoke(error)
        },
    ) {
        supervisorScope { doOnLaunch() }
    }
        .apply {
            doFinally?.let { doFinally ->
                invokeOnCompletion {
                    doFinally()
                }
            }
        }