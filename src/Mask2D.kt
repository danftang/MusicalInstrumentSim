class Mask2D(val data : BooleanArray, val xSize : Int) {
    val ySize : Int
        get() = data.size/xSize

    constructor(xDimension: Int, yDimension: Int) :
            this(BooleanArray(xDimension*yDimension), xDimension)

    constructor(xDimension: Int, yDimension: Int, initialiser : (Int,Int) -> Boolean) :
            this(BooleanArray(xDimension*yDimension, {i ->
                initialiser(i%xDimension,i/xDimension)
            }), xDimension)

    operator fun get(i : Int, j : Int) : Boolean {
        if(i < 0 || i>=xSize || j<0 || j>ySize) return false
        return data.get(i + j*xSize)
    }

    operator fun set(i : Int, j : Int, value : Boolean) {
        data.set(i + j*xSize, value)
    }
}