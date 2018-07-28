import java.awt.Dimension

class DoubleArray2D(val data : DoubleArray, val xSize : Int) {

    val ySize : Int
        get() = data.size/xSize

    constructor(xDimension: Int, yDimension: Int) :
            this(DoubleArray(xDimension*yDimension), xDimension) {}

    constructor(xDimension: Int, yDimension: Int, initialiser : (Int,Int) -> Double) :
            this(DoubleArray(xDimension*yDimension, {i ->
                initialiser(i%xDimension,i/xDimension)
            }), xDimension) {}

    operator fun get(i : Int, j : Int) : Double {
        return data.get(i + j*xSize)
    }

    operator fun set(i : Int, j : Int, value : Double) {
        data.set(i + j*xSize, value)
    }



}