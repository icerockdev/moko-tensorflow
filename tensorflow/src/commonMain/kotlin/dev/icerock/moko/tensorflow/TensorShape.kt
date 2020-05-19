package dev.icerock.moko.tensorflow

inline class TensorShape(
    val dimensions: IntArray
) {
    val rank: Int
        get() = dimensions.size
}
