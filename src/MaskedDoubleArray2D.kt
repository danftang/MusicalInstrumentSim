open class MaskedDoubleArray2D(val data : DoubleArray2D, val mask : Mask2D) {
    constructor(mask : Mask2D) :
            this(DoubleArray2D(mask.xSize, mask.ySize), mask)

    constructor(mask : Mask2D, initialiser : (Int,Int) -> Double) :
            this(DoubleArray2D(mask.xSize, mask.ySize, initialiser), mask)


    operator fun get(i : Int, j : Int) : Double {
        return if(mask[i,j]) data.get(i, j) else 0.0
    }

    operator fun set(i : Int, j : Int, value : Double) {
        if(mask[i,j]) data.set(i, j, value)
    }
}