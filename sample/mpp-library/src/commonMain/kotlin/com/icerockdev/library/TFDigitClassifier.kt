/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library

import dev.icerock.moko.tensorflow.Interpreter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TFDigitClassifier(
    private val interpreter: Interpreter,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) {

    var inputImageWidth: Int = 0 // will be inferred from TF Lite model
        private set
    var inputImageHeight: Int = 0 // will be inferred from TF Lite model
        private set
    var modelInputSize: Int = 0 // will be inferred from TF Lite model
        private set

    fun initialize() {
        val inputShape = interpreter.getInputTensor(0).shape
        inputImageWidth = inputShape[1]
        inputImageHeight = inputShape[2]
        modelInputSize = FLOAT_TYPE_SIZE * inputImageWidth * inputImageHeight * PIXEL_SIZE
    }

    fun classifyAsync(inputData: Any, onResult: (String) -> Unit) {
        scope.launch(Dispatchers.Default) {

            val result = Array(1) { FloatArray(OUTPUT_CLASSES_COUNT) }
            interpreter.run(listOf(inputData), mapOf(Pair(0, result)))

            val maxIndex = result[0].indices.maxByOrNull { result[0][it] } ?: -1
            val strResult = "Prediction Result: $maxIndex\nConfidence: ${result[0][maxIndex]}"

            onResult(strResult)
        }
    }

    companion object {
        private const val FLOAT_TYPE_SIZE = 4
        private const val PIXEL_SIZE = 1

        private const val OUTPUT_CLASSES_COUNT = 10
    }
}
