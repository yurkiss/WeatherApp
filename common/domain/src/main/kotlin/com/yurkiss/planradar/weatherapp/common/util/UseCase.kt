package com.yurkiss.planradar.weatherapp.common.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
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

abstract class FlowUseCase<out Type, in Params> {

    protected abstract fun run(params: Params): Flow<Type>

    operator fun invoke(params: Params): Flow<Type> {
        return run(params)
    }

}

abstract class NoParamsFlowUseCase<out Type> : FlowUseCase<Type, Nothing>()
