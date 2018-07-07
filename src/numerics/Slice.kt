package numerics

class Slice<DATA>(val data : IMutableArray<DATA>, val offset : Int, val length : Int) : IMutableArray<DATA> {
    override fun get(i: Int): DATA {
        return data.get(i + offset)
    }

    override fun set(i: Int, v: DATA) {
        data.set(i+offset, v)
    }

    override fun size(): Int {
        return length
    }
}