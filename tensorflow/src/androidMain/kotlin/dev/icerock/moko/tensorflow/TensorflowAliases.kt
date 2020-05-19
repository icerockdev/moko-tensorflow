/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

import org.tensorflow.lite.Delegate
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.Tensor

typealias PlatformInterpreter = Interpreter
typealias PlatformInterpreterOptions = Interpreter.Options
typealias PlatformTensorFlowDelegate = Delegate
typealias PlatformTensor = Tensor
