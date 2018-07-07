package numerics
class UnaryOpField<DATA : DoubleLike<DATA>>(var f: IField<DATA>,
                                            var op: (DATA) -> DATA) : IField<DATA> {


    override var domain = f.domain

    override fun get(i: Int, j: Int): DATA {
        return op(f.get(i, j))
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