/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.posix.memcpy

@Suppress("TooGenericExceptionThrown")
internal fun <T> errorHandled(block: (CPointer<ObjCObjectVar<NSError?>>) -> T?): T? {
    val (result, error) = memScoped {
        val errorPtr = alloc<ObjCObjectVar<NSError?>>()
        // need somehow to print trace
        runCatching { block(errorPtr.ptr) }
            .onFailure { it.printStackTrace() }
            .getOrNull() to errorPtr.value
    }
    if (error != null) throw Exception(error.description)
    return result
}

internal fun NSData.toUByteArray(): UByteArray = UByteArray(this.length.toInt()).apply {
    usePinned {
        memcpy(it.addressOf(0), this@toUByteArray.bytes, this@toUByteArray.length)
    }
}

@Suppress("MagicNumber")
internal fun bytesToIntBits(bytes: List<Byte>): Int {
    return (bytes[0].toInt() shl 24)
        .or(bytes[1].toInt() and 0xFF shl 16)
        .or(bytes[2].toInt() and 0xFF shl 8)
        .or(bytes[3].toInt() and 0xFF)
}
