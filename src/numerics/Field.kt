package numerics

class Field<DATA : DoubleLike<DATA>>(val data : IMutableArray<DATA>, val leftBoundary: IBoundary<DATA>, val rightBoundary: IBoundary<DATA>) : IMutableArray<DATA> {
    override fun set(i: Int, v: DATA) {
        data.set(i,v)
    }

    override fun get(i: Int): DATA {
        if(i < 0) return leftBoundary[-i]
        if(i >= data.size()) return rightBoundary[i-data.size()]
        return data[i]
    }

    override fun size(): Int {
        return data.size()
    }

//
//    override var domain: Domain<DATATYPE>
//
//    constructor(iSize: Int, jSize: Int, domain: Domain<DATATYPE>, vals: (Int, Int) -> DATATYPE) :
//            super(iSize, jSize, vals) {
//        this.domain = domain.clone()
//        attachBoundaries()
//    }
//
//    constructor(iField: IField<DATATYPE>) :
//            super(iField.iSize(), iField.jSize(), { i, j -> iField.get(i, j) }) {
//        //this.domain = iField.getDomain().clone()
//        this.domain = iField.domain.clone()
//        attachBoundaries()
//    }
//
//    constructor(array2D: Array2D<DATATYPE>, domain: Domain<DATATYPE>) :
//            super(array2D.iSize(), array2D.jSize(), { i, j -> array2D.get(i, j) }) {
//        this.domain = domain
//        attachBoundaries()
//    }
//
//    private fun attachBoundaries() {
//        domain.SBoundary.attachTo(this);
//        domain.NBoundary.attachTo(Rotate180(this))
//        domain.EBoundary.attachTo(RotateClockwise90(this))
//        domain.WBoundary.attachTo(RotateAntiClockwise90(this))
//    }
//
//    operator fun plusAssign(other: IArray2D<DATATYPE>) {
//        for (i in 0 until iSize()) {
//            for (j in 0 until jSize()) {
//                set(i, j, get(i, j) + other.get(i, j))
//            }
//        }
//    }
//
//    fun set(other: IField<DATATYPE>) {
//        for (i in 0 until iSize()) {
//            for (j in 0 until jSize()) {
//                set(i, j, other.get(i, j))
//            }
//        }
//    }
}

