/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

import cocoapods.TFLTensorFlowLite.TFLInterpreterOptions

actual class InterpreterOptions actual constructor(numThreads: Int) {

    internal val tflInterpreterOptions = TFLInterpreterOptions().apply {
        setNumberOfThreads(numThreads.toULong())
    }
}
