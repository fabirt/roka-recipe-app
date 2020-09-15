package com.fabirt.roka.core.error

import retrofit2.HttpException
import java.net.UnknownHostException

class HttpLimitExceededException : Exception()

fun Throwable.toFailure(): Failure {
    return when (this) {
        is HttpException -> Failure.ServerFailure
        is HttpLimitExceededException -> Failure.LimitExceededFailure
        is UnknownHostException -> Failure.NetworkFailure
        else -> Failure.UnexpectedFailure
    }
}