/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

import android.content.Context
import dev.icerock.moko.resources.FileResource

actual class Interpreter(
    actual val fileResource: FileResource,
    actual val options: InterpreterOptions,
    context: Context
) {

    private val tensorFlowInterpreter = PlatformInterpreter(
        fileResource.openAsFile(context),
        options.tensorFlowInterpreterOptions
    )

    /**
     * Gets the number of input tensors.
     */
    actual fun getInputTensorCount(): Int = tensorFlowInterpreter.inputTensorCount

    /**
     * Gets the number of output Tensors.
     */
    actual fun getOutputTensorCount(): Int = tensorFlowInterpreter.outputTensorCount

    /**
     * Gets the Tensor associated with the provdied input index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    actual fun getInputTensor(index: Int): Tensor = tensorFlowInterpreter.getInputTensor(index).toTensor()

    /**
     * Gets the Tensor associated with the provdied output index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    actual fun getOutputTensor(index: Int): Tensor = tensorFlowInterpreter.getOutputTensor(index).toTensor()

    /**
     * Resizes [index] input of the native model to the given [shape].
     */
    actual fun resizeInput(index: Int, shape: TensorShape) {
        tensorFlowInterpreter.resizeInput(index, shape.dimensions)
    }

    /**
     * Runs model inference if the model takes multiple inputs, or returns multiple outputs.
     */
    actual fun run(inputs: List<Any>, outputs: Map<Int, Any>) {
        tensorFlowInterpreter.runForMultipleInputsOutputs(inputs.toTypedArray(), outputs)
    }

    /**
     * Release resources associated with the [Interpreter].
     */
    actual fun close() {
        tensorFlowInterpreter.close()
    }
}
