package FiniteDifference

class Dirichlet(val v : Double) : IBoundary<Double> {
    override operator fun get(i: Int): Double {
        return v
    }
}