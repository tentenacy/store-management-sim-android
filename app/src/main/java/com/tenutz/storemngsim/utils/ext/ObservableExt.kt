package com.tenutz.storemngsim.utils.ext

import android.widget.EditText
import com.jakewharton.rxbinding4.widget.textChanges
import com.tenutz.storemngsim.utils.constant.RETRY_PREDICATE
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.kotlin.Flowables
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.rx3.await
import java.util.concurrent.TimeUnit


fun <T : Any> Single<T>.retryThreeTimes(): Single<T> = retryWhen { attempts ->
    Flowables.zip(
        attempts.map { error -> if (error is Exception) error else throw error },
        Flowable.interval(1, TimeUnit.SECONDS)
    ).map { (error, retryCount) -> if (retryCount >= 3) throw error }
}

fun <T : Any> applyRetryPolicyContinuous(
    vararg predicates: RETRY_PREDICATE,
    maxRetries: Long = RetryPolicyConstant.MAX_RETRIES,
    interval: Long = RetryPolicyConstant.DEFAULT_INTERVAL,
    unit: TimeUnit = TimeUnit.SECONDS,
) = SingleTransformer<T, T> { single ->
    single.retryWhen { attempts ->
        Flowables.zip(
            attempts.map { error -> if (predicates.count { it(error) } > 0) error else throw error },
            Flowable.interval(interval, unit)
        ).map { (error, retryCount) -> if (retryCount >= maxRetries) throw error }
    }
}

fun <T : Any> applyRetryPolicy(
    vararg predicates: RETRY_PREDICATE,
    maxRetries: Long = RetryPolicyConstant.MAX_RETRIES,
    interval: Long = RetryPolicyConstant.DEFAULT_INTERVAL,
    unit: TimeUnit = TimeUnit.SECONDS,
    errorReturn: (Throwable) -> T
) = SingleTransformer<T, T> { single ->
    single.retryWhen { attempts ->
        Flowables.zip(
            attempts.map { error -> if (predicates.count { it(error) } > 0) error else throw error },
            Flowable.interval(interval, unit)
        ).map { (error, retryCount) -> if (retryCount >= maxRetries) throw error }
    }
        .onErrorReturn(errorReturn)
}

fun <T : Any> Single<T>.toDeferred(scope: CoroutineScope) = scope.async { await() }

fun editTextObservable(editText: EditText): Observable<String> {
    return editText.textChanges().map { charSequence -> charSequence.toString() }
}