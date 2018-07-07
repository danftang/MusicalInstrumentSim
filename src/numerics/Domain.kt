package numerics
class Domain<DATA : DoubleLike<DATA>>(var DX: Double, var DY: Double,
                                      var NBoundary: IBoundary<DATA>,
                                      var SBoundary: IBoundary<DATA>,
                                      var EBoundary: IBoundary<DATA>,
                                      var WBoundary: IBoundary<DATA>) {


    fun clone(): Domain<DATA> {
        return Domain(DX, DY, NBoundary.clone(), SBoundary.clone(),
                EBoundary.clone(), WBoundary.clone())
    }
}
