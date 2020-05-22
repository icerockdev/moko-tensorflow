/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library

import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.sample.tensorflowtest.MR

object ResHolder {
    fun getDigitsClassifierModel(): FileResource {
        return MR.files.mnist
    }
}
