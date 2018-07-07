package numerics

interface IImmutableArray<T> {
    operator fun get(i: Int) : T
    fun size() : Int

}