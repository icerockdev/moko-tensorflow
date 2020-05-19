/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.tensorflow

import android.content.Context
import android.content.res.Resources
import dev.icerock.moko.resources.FileResource
import java.io.File

fun FileResource.openAsFile(context: Context): File {
    val resources: Resources = context.resources
    return resources.openRawResource(rawResId).use { inputStream ->
        val tmpFile = File.createTempFile("prefix", "suffix", context.cacheDir)
        tmpFile.outputStream().use {
            inputStream.copyTo(it)
        }
        tmpFile
    }
}
