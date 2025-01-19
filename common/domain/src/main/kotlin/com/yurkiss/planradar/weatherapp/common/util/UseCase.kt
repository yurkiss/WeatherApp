package com.yurkiss.planradar.weatherapp.common.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


abstract class UseCase<out Type, in Params>(
    protected val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    protected abstract suspend fun run(params: Params): Type

    operator fun invoke(
        params: Params,
        scope: CoroutineScope = MainScope(),
        onResult: (Type) -> Unit = {}
    ) {
        scope.launch {
            val deferredJob = async(dispatcher) {
                run(params)
            }
            onResult(deferredJob.await())
        }
    }

}

abstract class NoParamsUseCase<out Type>(
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : UseCase<Type, Unit>(dispatcher) {

    operator fun invoke(
        scope: CoroutineScope = MainScope(),
        onResult: (Type) -> Unit = {}
    ) {
        invoke(Unit, scope, onResult)
    }

}
