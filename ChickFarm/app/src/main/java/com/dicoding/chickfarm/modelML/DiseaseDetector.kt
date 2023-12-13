package com.dicoding.chickfarm.modelML

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class DiseaseDetector(context: Context, modelPath: String) {

    private val interpreter: Interpreter


    init {
        val options = Interpreter.Options()


        interpreter = Interpreter(loadModelFile(context, modelPath), options)
    }

    @Throws(IOException::class)
    private fun loadModelFile(context: Context, modelName: String): ByteBuffer {
        val assetFileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun predict(inputData: Array<Array<FloatArray>>?): Int {
        // Inisialisasi ByteBuffer untuk input
        val inputBuffer = ByteBuffer.allocateDirect(224 * 224 * 3 *4 )
        inputBuffer.order(ByteOrder.nativeOrder())

        // Salin data dari inputData ke inputBuffer
        for (i in 0 until 224) {
            for (j in 0 until 224) {
                for (k in 0 until 3) {
                    inputBuffer.putFloat(inputData!![i][j][k])
                }
            }
        }

        // Persiapkan buffer untuk output
        val outputBuffer = Array(1) { FloatArray(4) }

        // Run inference
        interpreter.run(inputBuffer, outputBuffer)
        val predictionResult = outputBuffer[0]
        // Mencari nilai maksimum
        val value = predictionResult.maxOrNull() ?: -1.0f

// Mencari indeks nilai maksimum
        val index = predictionResult.indexOfFirst { it == value }
        Log.d("Prediction", "Max Value: $value, Max Index: $index  Result: ${predictionResult.size}")

        for(i in 0 until 4){

        Log.d("index", "${predictionResult.get(i)}")
        }
        return index
    }


}
