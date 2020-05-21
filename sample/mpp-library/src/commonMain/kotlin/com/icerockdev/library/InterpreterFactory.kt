package com.icerockdev.library

import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.sample.tensorflowtest.MR

class ResHolder {
    fun getDigitsClassifierModel(): FileResource {
        return MR.files.mnist
    }
}
