/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSError

internal fun <T> errorHandled(block: (CPointer<ObjCObjectVar<NSError?>>) -> T?): T? {
    val (result, error) = memScoped {
        val errorPtr = alloc<ObjCObjectVar<NSError?>>()
        runCatching {
            block(errorPtr.ptr)
        }.getOrNull() to errorPtr.value
    }
    if(error != null) throw Exception(error.description)
    return result
}
