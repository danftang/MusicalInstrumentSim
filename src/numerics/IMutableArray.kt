package numerics

interface IMutableArray<T> : IImmutableArray<T> {
    operator fun set(i: Int, v: T)
}
