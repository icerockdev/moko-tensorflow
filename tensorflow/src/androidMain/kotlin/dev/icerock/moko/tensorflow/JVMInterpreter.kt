/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

import android.content.Context
import dev.icerock.moko.resources.FileResource

class JVMInterpreter(
    override val fileResource: FileResource,
    override val options: InterpreterOptions,
    context: Context
) : Interpreter {

    private val tensorFlowInterpreter = PlatformInterpreter(
        fileResource.openAsFile(context),
        options.tensorFlowInterpreterOptions
    )

    /**
     * Gets the number of input tensors.
     */
    override fun getInputTensorCount(): Int = tensorFlowInterpreter.inputTensorCount

    /**
     * Gets the number of output Tensors.
     */
    override fun getOutputTensorCount(): Int = tensorFlowInterpreter.outputTensorCount

    /**
     * Gets the Tensor associated with the provdied input index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    override fun getInputTensor(index: Int): Tensor {
        return tensorFlowInterpreter.getInputTensor(index).toTensor()
    }


    /**
     * Gets the Tensor associated with the provdied output index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    override fun getOutputTensor(index: Int): Tensor {
        return tensorFlowInterpreter.getOutputTensor(index).toTensor()
    }


    /**
     * Resizes [index] input of the native model to the given [shape].
     */
    override fun resizeInput(index: Int, shape: TensorShape) {
        tensorFlowInterpreter.resizeInput(index, shape.dimensions)
    }

    override fun allocateTensors() {
        tensorFlowInterpreter.allocateTensors()
    }

    /**
     * Runs model inference if the model takes multiple inputs, or returns multiple outputs.
     */
    override fun run(inputs: Array<*>, outputs: Map<Int, Any>) {
        tensorFlowInterpreter.runForMultipleInputsOutputs(inputs, outputs)
    }

    override fun run(nativeInput: NativeInput, output: Array<*>) {
        val inputs = arrayOf(nativeInput.byteBuffer)
        val outputs = mapOf(Interpreter.OUTPUT_KEY to output)
        run(inputs, outputs)
    }

    /**
     * Release resources associated with the [JVMInterpreter].
     */
    override fun close() {
        tensorFlowInterpreter.close()
    }
}
