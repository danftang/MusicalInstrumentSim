package numerics

class SliceArray<DATA>(val data : IMutableArray<DATA>, val stride : Int) : IMutableArray<IMutableArray<DATA>> {
    override fun get(i: Int): IMutableArray<DATA> {
        return Slice(data, i*stride, stride)
    }

    override fun set(i: Int, v: IMutableArray<DATA>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun size(): Int {
        return data.size() / stride
    }

}