class MaskedDoubleArray2D(val data : DoubleArray2D, val mask : Mask2D) {
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


    // ...for boundary condition d2_dx2 + d2_dy2 = 0
    // and d3_dx3 + k*d3_dxdy2 = 0
    // with d2_dxdy = 0 at corner
    fun d4_dx4(i : Int, j : Int) : Double {
        if(!mask[i+1,j]) {
            if(!mask[i-1,j] || !mask[i-2,j]) throw(ArithmeticException("Found illegal mask while calculating fourth derivative (right)"))
        }
        if(!mask[i+2,j]) {
            return d2_dx2(i-1,j) - 2.0*d2_dx2(i,j) - d2_dy2(i+1, j) // enforce d2_dx2 + d2_dy2 = 0 along right edge
        }
        if(!mask[i-1,j]) {
            if(!mask[i+1,j] || !mask[i+2,j]) throw(ArithmeticException("Found illegal mask while calculating fourth derivative (left)"))

        }
        if(!mask[i-2,j]) {
            return d2_dx2(i+1,j) - 2.0*d2_dx2(i,j) - d2_dy2(i-1, j) // enforce d2_dx2 + d2_dy2 = 0 along left edge
        }
    }

    // ...for boundary condition d2_dx2 + d2_dy2 = 0
    fun d2_dx2(i : Int, j : Int) : Double {
        if(!mask[i,j]) throw(ArithmeticException("Trying to differentiate a masked point"))
        if(!mask[i+1,j]) {
            // on a right edge
            if(!mask[i,j+1]) {
                // on a top-right corner
                if(!mask[i,j-1] || !mask[i-1,j]) throw(ArithmeticException("Found illegal mask while calculating second derivative (top right)"))
                return 2.0*this[i,j] - 0.5*(this[i-1,j] + this[i,j-1])
            }
            if(!mask[i,j-1]) {
                // on a bottom-right corner
                if(!mask[i-1,j]) throw(ArithmeticException("Found illegal mask while calculating second derivative (bottom right)"))
                return 2.0*this[i,j] - 0.5*(this[i-1,j] + this[i,j+1])
            }
            return -d2_dy2(i,j) // enforce boundary condition d2_dx2 + dy_dy2 = 0
        }
        if(!mask[i-1,j]) {
            // on a left edge
            if(!mask[i,j+1]) {
                // on a top-left corner
                if(!mask[i,j-1]) throw(ArithmeticException("Found illegal mask while calculating second derivative (top left)"))
                return 2.0*this[i,j] - 0.5*(this[i+1,j] + this[i,j-1])
            }
            if(!mask[i,j-1]) {
                // on a bottom-left corner
                return 2.0*this[i,j] - 0.5*(this[i+1,j] + this[i,j+1])
            }
            return -d2_dy2(i,j)
        }
        return (this[i+1,j] - 2.0*this[i,j] + this[i-1,j])
    }


    fun d2_dy2(i : Int, j : Int) : Double {
        if(!mask[i,j]) throw(ArithmeticException("Trying to differentiate a masked point"))
        if(!mask[i,j-1] || !mask[i,j+1]) return -d2_dx2(i,j) // enforce boundary condition d2_dx2 + dy_dy2 = 0
        return (this[i,j+1] - 2.0*this[i,j] + this[i,j-1])
    }
}