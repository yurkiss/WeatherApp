package com.yurkiss.planradar.weatherapp.common.util

import kotlin.reflect.KClass

/**
 * A BiDiMapping defines a reusable bidirectional transformation between an input and output type
 */
open class BiDiMapping<IN, OUT : Any>(
    val clazz: KClass<OUT>,
    val asOut: (IN) -> OUT,
    val asIn: (OUT) -> IN
) {

    inline fun <reified NEXT : Any> map(
        crossinline nextOut: (OUT) -> NEXT,
        crossinline nextIn: (NEXT) -> OUT
    ): BiDiMapping<IN, NEXT> =
        BiDiMapping(NEXT::class, { nextOut(asOut(it)) }, { asIn(nextIn(it)) })

    @JvmName("asIn")
    operator fun invoke(out: OUT): IN = asIn(out)

    @JvmName("asOut")
    operator fun invoke(asIn: IN): OUT = asOut(asIn)

    companion object {
        inline operator fun <IN, reified T : Any> invoke(
            noinline asOut: (IN) -> T,
            noinline asIn: (T) -> IN
        ) = BiDiMapping(T::class, asOut, asIn)
    }
}