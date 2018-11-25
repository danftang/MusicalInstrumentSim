class MaskedDoubleArray2D(val data : DoubleArray2D, val mask : Mask2D, val nu : Double) {
    constructor(mask : Mask2D, nu : Double) :
            this(DoubleArray2D(mask.xSize, mask.ySize), mask, nu)

    constructor(mask : Mask2D, nu : Double, initialiser : (Int,Int) -> Double) :
            this(DoubleArray2D(mask.xSize, mask.ySize, initialiser), mask, nu)


    operator fun get(i : Int, j : Int) : Double {
        return if(mask[i,j]) data.get(i, j) else 0.0
    }

    operator fun set(i : Int, j : Int, value : Double) {
        if(mask[i,j]) data.set(i, j, value)
    }


    // ...for boundary condition d2_dx2 + d2_dy2 = 0
    // and d3_dx3 + k*d3_dxdy2 = 0
    // with d2_dxdy = 0 at corner
//    fun d4_dx4(i : Int, j : Int) : Double {
//        if(!mask[i+1,j]) {
//            if(!mask[i-1,j] || !mask[i-2,j]) throw(ArithmeticException("Found illegal mask while calculating fourth derivative (right)"))
//        }
//        if(!mask[i+2,j]) {
//            return d2_dx2(i-1,j) - 2.0*d2_dx2(i,j) - d2_dy2(i+1, j) // enforce d2_dx2 + d2_dy2 = 0 along right edge
//        }
//        if(!mask[i-1,j]) {
//            if(!mask[i+1,j] || !mask[i+2,j]) throw(ArithmeticException("Found illegal mask while calculating fourth derivative (left)"))
//
//        }
//        if(!mask[i-2,j]) {
//            return d2_dx2(i+1,j) - 2.0*d2_dx2(i,j) - d2_dy2(i-1, j) // enforce d2_dx2 + d2_dy2 = 0 along left edge
//        }
//    }

    fun d4_dx4(i : Int, j : Int) : Double {
        if(!mask[i-2,j]) {
            return d2_dx2(i-1,j) -2.0*this[i-1,j] + 5.0*this[i,j] -4.0*this[i+1,j] + this[i+2,j]
        }
        if(!mask[i+2,j]) {
            return d2_dx2(i+1,j) -2.0*this[i+1,j] + 5.0*this[i,j] -4.0*this[i-1,j] + this[i-2,j]
        }
        return this[i-2,j] + this[i+2,j] - 4.0*(this[i-1,j] + this[i+1,j]) + 6.0*this[i,j]
    }

    fun d4_dy4(i : Int, j : Int) : Double {
        if(!mask[i,j-2]) {
            return d2_dy2(i,j-1) -2.0*this[i,j-1] + 5.0*this[i,j] -4.0*this[i,j+1] + this[i,j+2]
        }
        if(!mask[i,j+2]) {
            return d2_dy2(i,j+1) -2.0*this[i,j+1] + 5.0*this[i,j] -4.0*this[i,j-1] + this[i,j-2]
        }
        return this[i,j-2] + this[i,j+2] - 4.0*(this[i,j-1] + this[i,j+1]) + 6.0*this[i,j]
    }

    // laplacian = d2_dx2 + d2_dy2
    fun laplacian(i : Int, j : Int) : Double {
        if(!mask[i,j]) {
            // off the edge
            // TODO: finish this
        }
        if(!mask[i-1,j] || !mask[i+1,j]) {
            // on left/right edge
            if(!mask[i,j-1] || !mask[i,j+1]) {
                // on corner
                return 0.0
            }
            return (1.0-nu)*d2_dy2(i,j)
        }
        if(!mask[i,j+1] || !mask[i,j-1]) {
            // on top/bottom edge
            return (1.0-nu)*d2_dx2(i,j)
        }

        return d2_dx2(i,j) + d2_dy2(i,j)
    }


    // ...for boundary condition d2_dx2 + nu*d2_dy2 = 0
    fun d2_dx2(i : Int, j : Int) : Double {
        if(!mask[i,j]) throw(ArithmeticException("Trying to take d2x_dx2 of a masked point"))
        if(!mask[i+1,j]) {
            // on a right edge
            if(!mask[i-1,j]) throw(ArithmeticException("Found illegal mask while calculating second derivative (isolated point)"))
            if(!mask[i,j+1] || !mask[i,j-1]) {
                // on a top-right or bottom-right corner
                return 0.0;
            }
            return -nu*d2_dy2(i,j) // enforce boundary condition d2_dx2 + dy_dy2 = 0
        }
        if(!mask[i-1,j]) {
            // on a left edge
            if(!mask[i,j+1] || !mask[i,j-1]) {
                // on a top-left or bottom-left corner
                return 0.0
            }
            return -nu*d2_dy2(i,j)
        }
        return (this[i+1,j] - 2.0*this[i,j] + this[i-1,j])
    }


    // ...for boundary condition nu*d2_dx2 + d2_dy2 = 0
    fun d2_dy2(i : Int, j : Int) : Double {
        if(!mask[i,j]) throw(ArithmeticException("Trying to differentiate a masked point"))
        if(!mask[i,j-1] || !mask[i,j+1]) return -nu*d2_dx2(i,j) // enforce boundary condition nu*d2_dx2 + dy_dy2 = 0
        return (this[i,j+1] - 2.0*this[i,j] + this[i,j-1])
    }
}