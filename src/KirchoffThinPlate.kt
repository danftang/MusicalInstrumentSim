class KirchoffThinPlate : MaskedDoubleArray2D {
    val nu : Double
    val delta : Double // distance between gridpoints
    constructor(nu : Double, xLength : Double, mask : Mask2D) : super(mask) {
        this.nu = nu
        this.delta = xLength/mask.xSize
    }

    constructor( nu : Double, xLength : Double, mask : Mask2D, initialiser : (Int,Int) -> Double) : super(mask, initialiser) {
        this.nu = nu
        this.delta = xLength/mask.xSize
    }

    fun LaplaceLaplace(i : Int, j : Int) : Double {
        if(!mask[i,j]) return 0.0 // masked point
        if(!mask[i-1,j]) { // left hand edge
            if(!mask[i,j+1]) { // top left corner
                // TODO
            }
            if(!mask[i,j-1]) { // bottom left corner
                // TODO
            }
            return LaplaceV(i+1,j) - (1.0-nu)*(1.0-nu)*d4_dy4(i,j)
        }
        if(!mask[i+1,j]) { // right hand edge
            if(!mask[i,j+1]) { // top right corner
                // TODO
            }
            if(!mask[i,j-1]) { // bottom right corner
                // TODO
            }
            return LaplaceV(i-1,j) - (1.0-nu)*(1.0-nu)*d4_dy4(i,j)

        }
        if(!mask[i,j+1]) { // top edge
            return LaplaceH(i,j-1) - (1.0-nu)*(1.0-nu)*d4_dx4(i,j)
        }
        if(!mask[i,j-1]) { // bottom edge
            return LaplaceH(i,j+1) - (1.0-nu)*(1.0-nu)*d4_dx4(i,j)
        }
        return Laplace(i+1,j) + Laplace(i-1,j) + Laplace(i,j+1) +
                Laplace(i,j-1) - 4.0*Laplace(i,j)
    }

    fun Laplace(i : Int, j : Int) : Double {
        if(!mask[i,j]) return 0.0 // masked point
        if(!mask[i+1,j] || !mask[i-1,j]) { // left/right hand edge
            if(!mask[i,j+1] || !mask[i,j-1]) return 0.0; // corner
            return (1.0 - nu)*(this[i,j-1] - 2.0*this[i,j] + this[i,j+1])
        }
        if(!mask[i,j+1] || !mask[i,j-1]) { // top/bottom edge
            return(1.0-nu)*(this[i-1,j] - 2.0*this[i,j] + this[i+1,j])
        }
        return this[i+1,j] + this[i-1,j] + this[i,j+1] + this[i,j-1] - 4.0*this[i,j]
    }

    fun d2_dx2(i : Int, j : Int) : Double {
        if(!mask[i-1,j] || !mask[i+1,j]) { // left/right edge
            if(!mask[i,j-1] || !mask[i,j+1]) return 0.0 // corner
            return -nu*(this[i,j-1] - 2.0*this[i,j] + this[i,j+1])
        }
        return this[i-1,j] - 2.0*this[i,j] + this[i+1,j]
    }

    fun d2_dy2(i : Int, j : Int) : Double {
        if(!mask[i,j-1] || !mask[i,j+1]) { // top/bottom edge
            if(!mask[i-1,j] || !mask[i+1,j]) return 0.0 // corner
            return -nu*(this[i-1,j] - 2.0*this[i,j] + this[i+1,j])
        }
        return this[i,j-1] - 2.0*this[i,j] + this[i,j+1]
    }

    fun d4_dy4(i : Int, j : Int) : Double {
        return d2_dy2(i,j-1) - 2.0*d2_dy2(i,j) + d2_dy2(i,j+1)
    }

    fun d4_dx4(i : Int, j : Int) : Double {
        return d2_dx2(i-1,j) - 2.0*d2_dx2(i,j) + d2_dx2(i+1,j)
    }

    // LaplaceV = d2_dx2 + nu*d2_dy2
    fun LaplaceV(i : Int, j : Int) : Double {
        if(!mask[i-1,j] || !mask[i+1,j]) return 0.0
        if(!mask[i,j-1] || !mask[i,j+1]) {
            return (1.0-nu*nu)*(this[i-1,j] - 2.0*this[i,j] + this[i+1,j])
        }
        return this[i-1,j] + this [i+1,j] +
                nu*(this[i,j+1] + this[i,j-1]) - (2.0+2.0*nu)*this[i,j]
    }

    // LaplaceH = nu*d2_dx2 + d2_dy2
    fun LaplaceH(i : Int, j : Int) : Double {
        if(!mask[i,j+1] || !mask[i,j-1]) return 0.0
        if(!mask[i-1,j] || !mask[i+1,j]) {
            return (1.0-nu*nu)*(this[i,j-1] - 2.0*this[i,j] + this[i,j+1])
        }
        return nu*(this[i-1,j] + this [i+1,j]) +
                this[i,j+1] + this[i,j-1] - (2.0+2.0*nu)*this[i,j]
    }

}