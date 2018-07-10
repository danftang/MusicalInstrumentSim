import java.io.FileWriter

fun main(args : Array<String>) {
    val string = GuitarString()
    val file = FileWriter("data.out")
    val DT = 0.0001

    string.hit(80) // hit the string
    for(t in 1..20000) {
        string.step(DT)
        file.write("${t*DT} ${string.forceOnBridge().toString()}\n")
    }
    file.close()
}

