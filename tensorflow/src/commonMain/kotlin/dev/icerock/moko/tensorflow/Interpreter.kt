/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

import dev.icerock.moko.resources.FileResource

interface Interpreter {
    val fileResource: FileResource
    val options: InterpreterOptions

    /**
     * Gets the number of input tensors.
     */
    fun getInputTensorCount(): Int

    /**
     * Gets the number of output Tensors.
     */
    fun getOutputTensorCount(): Int

    /**
     * Gets the Tensor associated with the provdied input index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    fun getInputTensor(index: Int): Tensor

    /**
     * Gets the Tensor associated with the provdied output index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    fun getOutputTensor(index: Int): Tensor

    /**
     * Resizes [index] input of the native model to the given [shape].
     */
    fun resizeInput(index: Int, shape: TensorShape)
    fun allocateTensors()

    /**
     * Runs model inference if the model takes multiple inputs, or returns multiple outputs.
     *
     * In case with ios [outputs] required to be the { 0: Array<Any> } structure
     *
     * In case with ios [inputs] required to be the Array<NSData> structure
     */
    @Deprecated("This approach may work differently on ios and android platform. Use run with NativeInput")
    fun run(inputs: Array<*>, outputs: Map<Int, Any>)

    /**
     * Runs model inference with native input data
     *
     * @param nativeInput - NSData or java's ByteBuffer
     * @param output - required output array
     */
    fun run(nativeInput: NativeInput, output: Array<*>)

    /**
     * Release resources associated with the [Interpreter].
     */
    fun close()

    companion object {
        /**
         * This is static output key which should be used when adding outputs data in [Interpreter.run]
         */
        const val OUTPUT_KEY = 0
    }
}
