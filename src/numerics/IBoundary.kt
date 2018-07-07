package numerics
interface IBoundary<DATA> {

    operator fun get(i: Int): DATA
    fun attachTo(f: IArray2D<DATA>)
    fun clone(): IBoundary<DATA>
}