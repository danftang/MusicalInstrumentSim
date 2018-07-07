package numerics

class DoubleArray(val data : kotlin.DoubleArray) : IMutableArray<Double> {

    constructor(i : Int) : this(kotlin.DoubleArray(i))
    constructor(i : Int, lambda : (Int) -> Double) : this(kotlin.DoubleArray(i, lambda))


    override fun get(i: Int): Double {
        return data.get(i)
    }

    override fun set(i: Int, v: Double) {
        data.set(i,v)
    }

    override fun size(): Int {
        return data.size
    }
}