/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.tensorflow.map.Float32Mapper
import platform.Foundation.NSData

@Suppress("ForbiddenComment")
class ObjCInterpreter(
    override val fileResource: FileResource,
    override val options: InterpreterOptions
) : Interpreter {

    private val tflInterpreter: PlatformInterpreter = errorHandled { errPtr ->
        PlatformInterpreter(fileResource.path, options.tflInterpreterOptions, errPtr)
    }!!

    /**
     * Gets the number of input tensors.
     */
    override fun getInputTensorCount(): Int {
        return tflInterpreter.inputTensorCount().toInt()
    }

    /**
     * Gets the number of output Tensors.
     */
    override fun getOutputTensorCount(): Int {
        return tflInterpreter.outputTensorCount().toInt()
    }

    /**
     * Gets the Tensor associated with the provdied input index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    override fun getInputTensor(index: Int): Tensor {
        return errorHandled { errPtr ->
            tflInterpreter.inputTensorAtIndex(index.toULong(), errPtr)
        }!!.toTensor()
    }

    /**
     * Gets the Tensor associated with the provdied output index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    override fun getOutputTensor(index: Int): Tensor {
        return errorHandled { errPtr ->
            tflInterpreter.outputTensorAtIndex(index.toULong(), errPtr)
        }!!.toTensor()
    }

    /**
     * Resizes [index] input of the native model to the given [shape].
     */
    override fun resizeInput(index: Int, shape: TensorShape) {
        errorHandled { errPtr ->
            tflInterpreter.resizeInputTensorAtIndex(
                index.toULong(),
                shape.getNSNumberDimensionList(),
                errPtr
            )
        }
    }

    override fun allocateTensors() {
        errorHandled { errPtr ->
            tflInterpreter.allocateTensorsWithError(errPtr)
        }
    }


    /**
     * Runs model inference if the model takes multiple inputs, or returns multiple outputs.
     *
     * TODO: need to implement [outputs] applying.
     */
    override fun run(
        inputs: Array<*>,
        outputs: Map<Int, Any>
    ) {
        require(outputs.size == 1) { "Output map should have the { 0: Array<Any> } structure" }
        require(outputs.containsKey(Interpreter.OUTPUT_KEY)) { "Output map should have the { 0: Array<Any> } structure" }
        require(outputs[Interpreter.OUTPUT_KEY] is Array<*>) { "Output map 0's key value is not Array<*>. Output map should have the{ 0: Array<Any> } structure" }
        require(inputs.size <= getInputTensorCount()) { "Wrong inputs dimension." }
        inputs.forEach { input -> require(input is NSData) { "ios interpterer only accpept NSData as an input" } }
        val nsInputs = inputs.map { it as NSData }
        val outputArray = outputs[Interpreter.OUTPUT_KEY] as Array<Any>
        // Filling input tensors of our interpreter with nsData
        nsInputs.forEachIndexed { index, nsData ->
            val inputTensor = getInputTensor(index)
            errorHandled { errPtr ->
                inputTensor.platformTensor.copyData(
                    nsData,
                    errPtr
                )
            }
        }
        errorHandled { errPtr ->
            tflInterpreter.invokeWithError(errPtr)
        }
        // Here we can get our output data
        nsInputs.indices.forEach { index ->
            val outputTensor = getOutputTensor(index)

            val array = when (outputTensor.dataType) {
                TensorDataType.FLOAT32 -> Float32Mapper.map(outputTensor.platformTensor)

                else -> error("ObjCInterpreter doesn't have convertor for ${outputTensor.dataType} yet")
            }
            // ~~TODO: hardcoded case, works only with digits sample~~
            // Actually works with every shape, which contract the [N,X,Y,...,Z]. Where N is batch size
            outputArray[index] = array
        }
    }

    override fun run(nativeInput: NativeInput, output: Array<*>) {
        val inputs = arrayOf(nativeInput.nsData) as Array<*>
        val outputs = mapOf(Interpreter.OUTPUT_KEY to output)
        run(inputs, outputs)
    }

    /**
     * Release resources associated with the [ObjCInterpreter].
     *
     * ObjectiveC interpreter doesn't have jvm-like close method
     */
    override fun close() = Unit


}
