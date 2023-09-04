/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.divyanshu.draw.widget.DrawView
import com.icerockdev.library.ResHolder
import com.icerockdev.library.TFDigitClassifier
import dev.icerock.moko.tensorflow.JVMInterpreter
import dev.icerock.moko.tensorflow.InterpreterOptions
import dev.icerock.moko.tensorflow.NativeInput
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("MagicNumber")
class MainActivity : AppCompatActivity() {

    private lateinit var drawView: DrawView
    private lateinit var clearButton: Button
    private lateinit var predictedTextView: TextView

    private lateinit var interpreter: JVMInterpreter
    private lateinit var digitClassifier: TFDigitClassifier

    private val isInterpreterInited = AtomicBoolean(false)

    @Suppress("UnnecessarySafeCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawView = findViewById(R.id.draw_view)
        drawView.setStrokeWidth(70.0f)
        drawView.setColor(Color.WHITE)
        drawView.setBackgroundColor(Color.BLACK)
        clearButton = findViewById(R.id.clear_button)
        predictedTextView = findViewById(R.id.predicted_text)

        clearButton.setOnClickListener {
            drawView.clearCanvas()
            predictedTextView.text = "Please draw a digit"
        }

        drawView?.setOnTouchListener { _, event ->
            drawView?.onTouchEvent(event)

            if (event.action == MotionEvent.ACTION_UP) {
                classifyDrawing()
            }

            true
        }

        interpreter = JVMInterpreter(ResHolder.getDigitsClassifierModel(), InterpreterOptions(2, useNNAPI = true), this)
        digitClassifier = TFDigitClassifier(interpreter, this.lifecycleScope)

        digitClassifier.initialize()
        isInterpreterInited.set(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        interpreter.close()
    }

    private fun classifyDrawing() {
        if (!isInterpreterInited.get()) return

        val rawBitmap = drawView.getBitmap()
        val bitmapToClassify = Bitmap.createScaledBitmap(
            rawBitmap,
            digitClassifier.inputImageWidth,
            digitClassifier.inputImageHeight,
            true
        )
        val byteBuffer = convertBitmapToByteBuffer(bitmapToClassify)
//        digitClassifier.classifyAsync(byteBuffer) {
//            runOnUiThread {
//                predictedTextView.text = it
//            }
//        }
        digitClassifier.classifyNativeAsync(NativeInput(byteBuffer)) {
            runOnUiThread {
                predictedTextView.text = it
            }
        }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(digitClassifier.modelInputSize)
        byteBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(digitClassifier.inputImageWidth * digitClassifier.inputImageHeight)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        for (pixelValue in pixels) {
            val r = (pixelValue shr 16 and 0xFF)
            val g = (pixelValue shr 8 and 0xFF)
            val b = (pixelValue and 0xFF)

            val normalizedPixelValue = (r + g + b) / 3.0f / 255.0f
            byteBuffer.putFloat(normalizedPixelValue)
        }

        return byteBuffer
    }
}
