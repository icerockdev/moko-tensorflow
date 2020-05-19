package dev.icerock.moko.tensorflow

import dev.icerock.moko.resources.FileResource

actual class Interpreter {
    actual val fileResource: FileResource
        get() = TODO("Not yet implemented")
    actual val options: InterpreterOptions
        get() = TODO("Not yet implemented")

    /**
     * Gets the number of input tensors.
     */
    actual fun getInputTensorCount(): Int {
        TODO("Not yet implemented")
    }

    /**
     * Gets the number of output Tensors.
     */
    actual fun getOutputTensorCount(): Int {
        TODO("Not yet implemented")
    }

    /**
     * Gets the Tensor associated with the provdied input index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    actual fun getInputTensor(index: Int): Tensor {
        TODO("Not yet implemented")
    }

    /**
     * Gets the Tensor associated with the provdied output index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    actual fun getOutputTensor(index: Int): Tensor {
        TODO("Not yet implemented")
    }

    /**
     * Resizes [index] input of the native model to the given [shape].
     */
    actual fun resizeInput(index: Int, shape: TensorShape) {
    }

    /**
     * Runs model inference if the model takes multiple inputs, or returns multiple outputs.
     */
    actual fun run(
        inputs: List<Any>,
        outputs: Map<Int, Any>
    ) {
    }

    /**
     * Release resources associated with the [Interpreter].
     */
    actual fun close() {
    }

}