package FiniteDifference

open class FiniteDiffArray(val data : DoubleArray,
                      val leftBoundary : IBoundary<Double>,
                      val rightBoundary : IBoundary<Double>) {

    constructor(size : Int,
                leftBoundary : IBoundary<Double> = Dirichlet(0.0),
                rightBoundary : IBoundary<Double> = Dirichlet(0.0)) :
            this(DoubleArray(size), leftBoundary, rightBoundary) {}

    constructor(size : Int, initialiser : (Int)->Double,
                leftBoundary : IBoundary<Double> = Dirichlet(0.0),
                rightBoundary : IBoundary<Double> = Dirichlet(0.0)) :
            this(DoubleArray(size, initialiser), leftBoundary, rightBoundary) {}

    operator fun get(i : Int) : Double {
        if(i<0) return leftBoundary[i]
        if(i>=data.size) return rightBoundary[i]
        return data[i]
    }

    operator fun set(i : Int, value : Double) {
        data[i] = value
    }

    fun d2_di2(i : Int) : Double {
        return (this[i+1] - 2.0*this[i] + this[i-1])
    }

    fun d4_di4(i : Int) : Double {
        return (this[i+2] + this[i-2] - 4.0*(this[i+1] + this[i-1]) + 6.0*this[i])
    }

}