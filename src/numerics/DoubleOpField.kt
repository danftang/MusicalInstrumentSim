package numerics
class DoubleOpField<DATA : DoubleLike<DATA>>(var f: IField<DATA>,
                                             var g: Double,
                                             var op: (DATA, Double) -> DATA) : IField<DATA> {

    override var domain = f.domain

    override fun get(i: Int, j: Int): DATA {
        return op(f.get(i, j), g)
    }

    override fun set(i: Int, j: Int, v: DATA) {
        f[i, j] = v
    }

    override fun iSize(): Int {
        return f.iSize()
    }

    override fun jSize(): Int {
        return f.jSize()
    }
}