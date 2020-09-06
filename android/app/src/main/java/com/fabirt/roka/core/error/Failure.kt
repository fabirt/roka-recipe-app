package com.fabirt.roka.core.error

sealed class Failure {
    abstract val key: Int

    object NetworkFailure : Failure() {
        override val key: Int
            get() = 1
    }

    object UnexpectedFailure : Failure() {
        override val key: Int
            get() = 2
    }

    object ServerFailure : Failure() {
        override val key: Int
            get() = 3
    }
}