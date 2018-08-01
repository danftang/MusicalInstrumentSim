package FiniteDifference

interface IBoundary<T> {
    operator fun get(i : Int) : T
}
