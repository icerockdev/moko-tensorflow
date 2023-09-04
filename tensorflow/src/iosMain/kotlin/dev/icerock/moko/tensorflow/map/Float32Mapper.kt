package dev.icerock.moko.tensorflow.map

import dev.icerock.moko.tensorflow.PlatformTensor
import dev.icerock.moko.tensorflow.TensorDataType
import dev.icerock.moko.tensorflow.errorHandled
import dev.icerock.moko.tensorflow.toUByteArray
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.posix.memcpy

object Float32Mapper : TensorDataTypeMapper<FloatArray> {
    override val type: TensorDataType = TensorDataType.FLOAT32
    private fun UByteArray.toFloatArray(): FloatArray {
        @Suppress("MagicNumber")
        val floatArr = FloatArray(this.size / 4)
        usePinned { src ->
            floatArr.usePinned { dst ->
                memcpy(dst.addressOf(0), src.addressOf(0), this.size.toULong())
            }
        }
        return floatArr
    }

    override fun map(tensor: PlatformTensor): FloatArray {
        return errorHandled { errPtr ->
            tensor.dataWithError(errPtr)
        }!!.toUByteArray().toFloatArray()
    }
}