package com.fabirt.roka.core.error

import android.content.Context
import com.fabirt.roka.R

sealed class Failure {
    abstract val key: Int

    object NetworkFailure : Failure() {
        override val key: Int
            get() = R.string.network_error
    }

    object LimitExceededFailure : Failure() {
        override val key: Int
            get() = R.string.limit_exceeded_error
    }

    object ServerFailure : Failure() {
        override val key: Int
            get() = R.string.server_error
    }

    object UnexpectedFailure : Failure() {
        override val key: Int
            get() = R.string.unexpected_error
    }

    fun translate(context: Context): String {
        return context.getString(key)
    }
}