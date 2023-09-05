package dev.icerock.moko.tensorflow.map

import dev.icerock.moko.tensorflow.PlatformTensor
import dev.icerock.moko.tensorflow.TensorDataType

/**
 * [TensorDataTypeMapper] required to easily(almost) add converters for objective-c Interpreter
 */
interface TensorDataTypeMapper<T> {
    val type: TensorDataType
    fun map(tensor: PlatformTensor): T
}
