
class Array3D<T>(val data : Array<T>, val xSize : Int, val ySize : Int) {

    val zSize : Int
    get() {
        return data.size/(xSize*ySize)
    }

    operator fun get(x : Int, y : Int, z : Int) : T {
        return data[x + (y + z*ySize)*xSize]
    }

    operator fun set(x : Int, y : Int, z : Int, value : T) {
        data[x + (y + z*ySize)*xSize] = value
    }

    fun set(v : T) {
        for(i in 0 until data.size) {
            data[i] = v
        }
    }

    inline fun set(f : (Int,Int,Int)->T) {
        var di = 0
        for(k in 0 until zSize) {
            for(j in 0 until ySize) {
                for(i in 0 until xSize) {
                    data[di++] = f(i,j,k)
                }
            }
        }
    }

}

inline fun <reified T> Array3D(xSize : Int, ySize : Int, zSize : Int, initialiser : (Int,Int,Int) -> T) : Array3D<T> {
    return Array3D(Array(xSize*ySize*zSize, {
        val yz = it / xSize
        initialiser(it % xSize, yz % ySize, yz / ySize)
    }), xSize, ySize)
}
