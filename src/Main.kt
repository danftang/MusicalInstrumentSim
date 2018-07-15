import kotlin.math.sin

fun main(args : Array<String>) {
    val string = GuitarString()
    val DT = 1.0/44100.0
    val steps = (4.0/DT).toInt()
    val audioData = DoubleArray(steps)

    string.hit(string.N/4) // hit the string
    for(t in 0 until steps) {
        string.step(DT)
        audioData[t] = string.forceOnBridge()
    }

    WavWriter.writeMonoWavFile(audioData, "data.wav")
}
