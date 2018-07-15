import java.io.ByteArrayInputStream
import java.io.File
import javax.sound.sampled.AudioFileFormat
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

object WavWriter {
    fun writeMonoWavFile(data: DoubleArray, filename: String) {
        val wavFile = File(filename)



        val maxAmplitude = data.fold(0.0, {max,x -> max(max, abs(x))})
        val byteData = ByteArray(data.size * 2, {i ->
            val shortVal = ((data[i/2]/maxAmplitude)*Short.MAX_VALUE).roundToInt()
            if(i%2 == 0) {
                (shortVal shr 8).toByte()
            } else {
                (shortVal and 255).toByte()
            }
        })
        val input = AudioInputStream(ByteArrayInputStream(byteData), AudioFormat(44100.0f, 16, 2, true, true), data.size.toLong())
        AudioSystem.write(input, AudioFileFormat.Type.WAVE, wavFile)
    }
}