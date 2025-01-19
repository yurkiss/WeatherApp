package com.yurkiss.planradar.weatherapp.common.util;

import com.yurkiss.planradar.weatherapp.common.util.Failure.FeatureFailure
import kotlin.coroutines.cancellation.CancellationException

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(val exception: Throwable? = null) {

    abstract val message: String

    init {
        if (exception is CancellationException) throw exception
    }

    data class Composite(
        val failures: List<Failure>,
        override val message: String = failures.joinToString(separator = "\n") { it.message }
    ) : Failure(null)

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(exception: Throwable? = null) : Failure(exception)
}

data class NetworkException(override val message: String, val ex: Throwable?) : Failure(ex)
data class DatabaseException(override val message: String, val ex: Throwable?) : Failure(ex)

inline fun <T, R> T.runCatching(
    handler: (Throwable) -> Failure,
    block: T.() -> R
): Outcome<R> {
    return try {
        Either.Right(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Either.Left(handler(e))
    }
}
