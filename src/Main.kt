fun main(args : Array<String>) {
    val bar = MetalBar()
    val steps = (2.0/bar.DT).toInt()
    val audioData = DoubleArray(steps)

    bar.hit(bar.N/4)
    for(t in 0 until steps) {
        bar.step()
        audioData[t] = bar.sound()
        if(t%4410 == 0) println(audioData[t])
    }

    WavWriter.writeMonoWavFile(audioData, "data.wav")
}

fun simulateSheet() {
    val sheet = ElasticSheet()
    val DT = 1.0/44100.0
    val steps = (1.5/DT).toInt()
    val audioData = DoubleArray(steps)

    sheet.hit(sheet.X/4, sheet.Y/4)
    for(t in 0 until steps) {
        sheet.step(DT)
        if(t%100 == 0) println(sheet.sound())
        audioData[t] = sheet.sound()
    }

    WavWriter.writeMonoWavFile(audioData, "data.wav")
}

fun simulateString() {
    val string = GuitarString()
    val DT = 1.0/44100.0
    val steps = (4.0/DT).toInt()
    val audioData = DoubleArray(steps)

    string.pluck(string.N/4) // hit the string
    for(t in 0 until steps) {
        string.step(DT)
        audioData[t] = string.forceOnBridge()
    }

    WavWriter.writeMonoWavFile(audioData, "data.wav")

}