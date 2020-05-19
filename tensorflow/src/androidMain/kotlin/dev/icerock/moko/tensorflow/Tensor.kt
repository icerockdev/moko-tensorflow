/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

import org.tensorflow.lite.DataType

actual class Tensor(
    private val platformTensor: PlatformTensor
) {
    actual val dataType: TensorDataType
        get() = platformTensor.dataType().toTensorDataType()
    actual val name: String
        get() = platformTensor.name()
    actual val shape: IntArray
        get() = platformTensor.shape()
}

private fun DataType.toTensorDataType() = when(this) {
    DataType.FLOAT32 -> TensorDataType.FLOAT32
    DataType.INT32 -> TensorDataType.INT32
    DataType.UINT8 -> TensorDataType.UINT8
    DataType.INT64 -> TensorDataType.INT64
    DataType.STRING -> throw IllegalArgumentException("STRING tensor data type not supported in MPP.")
    DataType.INT8 -> throw IllegalArgumentException("INT8 tensor data type not supported in MPP.")
}
