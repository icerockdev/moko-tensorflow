/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

import cocoapods.TFLTensorFlowLite.TFLTensorDataType
import platform.Foundation.NSNumber

actual class Tensor(
    internal val platformTensor: PlatformTensor
) {
    actual val dataType: TensorDataType
        get() = platformTensor.dataType.toTensorDataType()

    actual val name: String
        get() = platformTensor.name()

    actual val shape: IntArray
        get() {
            val rawShapeArr = errorHandled { errPtr ->
                platformTensor.shapeWithError(errPtr)
            } as List<NSNumber>

            return rawShapeArr.map {
                it.unsignedIntValue().toInt()
            }.toIntArray()
        }
}

private fun TFLTensorDataType.toTensorDataType() = when (this) {
    TFLTensorDataType.TFLTensorDataTypeFloat32 -> TensorDataType.FLOAT32
    TFLTensorDataType.TFLTensorDataTypeInt32 -> TensorDataType.INT32
    TFLTensorDataType.TFLTensorDataTypeUInt8 -> TensorDataType.UINT8
    TFLTensorDataType.TFLTensorDataTypeInt64 -> TensorDataType.INT64

    TFLTensorDataType.TFLTensorDataTypeFloat16 -> throw IllegalArgumentException("TFLTensorDataTypeFloat16 not supported.")
    TFLTensorDataType.TFLTensorDataTypeBool -> throw IllegalArgumentException("TFLTensorDataTypeBool not supported.")
    TFLTensorDataType.TFLTensorDataTypeInt16 -> throw IllegalArgumentException("TFLTensorDataTypeInt16 not supported.")
    TFLTensorDataType.TFLTensorDataTypeInt8 -> throw IllegalArgumentException("TFLTensorDataTypeInt8 not supported.")
    TFLTensorDataType.TFLTensorDataTypeNoType -> throw IllegalArgumentException("TFLTensorDataTypeNoType: wrong tensor type.")
}
