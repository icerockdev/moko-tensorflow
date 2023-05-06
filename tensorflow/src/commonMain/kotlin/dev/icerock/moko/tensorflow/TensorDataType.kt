/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

@Suppress("MagicNumber")
enum class TensorDataType(val value: Int) {
    FLOAT32(1),
    INT32(2),
    UINT8(3),
    INT64(4),
    STRING(5),
    BOOL(6),
    INT16(7),
    INT8(9);

    fun byteSize(): Int = when (this) {
        FLOAT32 -> 4
        INT32 -> 4
        INT16 -> 2
        INT8 -> 1
        UINT8 -> 1
        INT64 -> 8
        BOOL -> -1
        STRING -> -1
    }
}
